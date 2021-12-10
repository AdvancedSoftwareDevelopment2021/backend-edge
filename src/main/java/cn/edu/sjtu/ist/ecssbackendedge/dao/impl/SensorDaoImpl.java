package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.SensorDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device.DeviceStatus;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.SensorStatus;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.device.DeviceStatusIotdbPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.sensor.SensorPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.Status;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.Sensor;
import cn.edu.sjtu.ist.ecssbackendedge.repository.SensorRepository;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.CollectSchedulerUtil;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.SensorUtil;

import cn.edu.sjtu.ist.ecssbackendedge.utils.storage.IotdbDeviceStatusUtil;
import cn.edu.sjtu.ist.ecssbackendedge.utils.storage.IotdbSensorStatusUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.Session;
import org.apache.iotdb.session.SessionDataSet;
import org.apache.iotdb.session.pool.SessionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief SensorDaoImpl
 * @date 2021-11-19
 */
@Slf4j
@Component
public class SensorDaoImpl implements SensorDao {

    @Autowired
    private Session session;

    @Autowired
    private SessionPool sessionPool;

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private SensorUtil sensorUtil;

    @Autowired
    private CollectSchedulerUtil collectSchedulerUtil;

    @Override
    public Sensor createSensor(Sensor sensor) {
        // 保存基本信息到 mongodb
        SensorPO sensorPO = sensorUtil.convertDomain2PO(sensor);
        sensorRepository.save(sensorPO);
        SensorPO po = sensorRepository.findSensorPOByDeviceIdAndName(sensor.getDeviceId(), sensor.getName());
        sensor.setId(po.getId());
        // 保存当前状态到 iotdb
        saveSensorStatus(sensor.getId(), sensor.getDeviceId(), sensor.getStatus().getType());
        return sensor;
    }

    @Override
    public void deleteSensorById(String sensorId) {
        // 删除 mongodb 中保存的信息
        sensorRepository.deleteSensorPOById(sensorId);
        // 删除 iotdb 中保存的信息
        removeSensorAllStatus(sensorId);
    }

    @Override
    public void deleteSensorByDeviceId(String deviceId) {
        List<SensorPO> sensorPOS = sensorRepository.findSensorPOSByDeviceId(deviceId);
        // 删除 mongodb 中保存的信息
        sensorRepository.deleteSensorPOSByDeviceId(deviceId);
        // 删除 iotdb 中保存的信息
        for (SensorPO po : sensorPOS) {
            removeSensorAllStatus(po.getId());
        }
    }

    @Override
    public void updateSensor(Sensor sensor) {
        SensorPO res = sensorRepository.findSensorPOById(sensor.getId());
        res.setName(sensor.getName());
        res.setCollectorScheduler(collectSchedulerUtil.convertDomain2PO(sensor.getCollectorScheduler()));
        res.setDataCollector(sensor.getDataCollector().convertDomain2PO());
        sensorRepository.save(res);

        // 保存当前状态到 iotdb
        saveSensorStatus(sensor.getId(), sensor.getDeviceId(), sensor.getStatus().getType());
    }

    @Override
    public void saveSensorStatus(String sensorId, String deviceId, String status) {
        String tableName = IotdbSensorStatusUtil.getSensorStatusTimeSeries(sensorId);
        List<String> measurements = Arrays.asList("deviceId", "status");
        List<String> values = Arrays.asList(deviceId, status);

        try {
            sessionPool.insertRecord(tableName, (new Date()).getTime(), measurements, values);
            log.info(String.format("iotdb: 插入状态成功！ 表名=%s", tableName));
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 插入传感器状态失败，传感器id=%s，状态=%s", sensorId, status));
            throw new RuntimeException("iotdb: 插入传感器状态失败，报错信息：" + e.getMessage());
        }
    }

    @Override
    public Sensor findSensorById(String sensorId) {
        SensorPO po = sensorRepository.findSensorPOById(sensorId);
        if (po == null) {
            throw new RuntimeException("数据采集过程不存在");
        }

        Sensor sensor = sensorUtil.convertPO2Domain(po);
        // 获取最新状态
        sensor.setStatus(Status.fromString(fetchLatestStatus(sensorId)));
        return sensor;
    }

    @Override
    public Sensor findSensorByDeviceIDAndName(String deviceId, String name) {
        SensorPO po = sensorRepository.findSensorPOByDeviceIdAndName(deviceId, name);
        if (po != null) {
            Sensor sensor = sensorUtil.convertPO2Domain(po);
            // 获取最新状态
            String status = fetchLatestStatus(sensor.getId());
            sensor.setStatus(Status.fromString(status));
            return sensor;
        }
        return null;
    }

    @Override
    public List<Sensor> findSensorsByDeviceId(String deviceId) {
        List<SensorPO> sensors = sensorRepository.findSensorPOSByDeviceId(deviceId);
        List<Sensor> res = new ArrayList<>();
        for (SensorPO sensorPO : sensors) {
            Sensor sensor = sensorUtil.convertPO2Domain(sensorPO);
            sensor.setStatus(Status.fromString(fetchLatestStatus(sensor.getId())));
            res.add(sensor);
        }
        return res;
    }

    public String fetchLatestStatus(String sensorId) {
        SensorStatus status = fetchLatestSensorStatus(sensorId);
        return status.getStatus();
    }

    @Override
    public SensorStatus fetchLatestSensorStatus(String sensorId) {
        try {
            String sql = IotdbSensorStatusUtil.sqlToSelectLatestStatus(sensorId);
            session.open();
            SessionDataSet sessionDataSet = session.executeQueryStatement(sql);
            SessionDataSet.DataIterator dataIterator = sessionDataSet.iterator();
            dataIterator.next();

            SensorStatus status = new SensorStatus();
            status.setTimestamp(dataIterator.getTimestamp("Time"));
            status.setStatus(dataIterator.getString(IotdbSensorStatusUtil.getSensorStatusTimeSeries(sensorId) + ".status"));

            session.close();
            log.info(String.format("iotdb: 查询最新状态成功！传感器id=%s，最新状态: %s", sensorId, status));
            return status;
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 查询最新状态失败，传感器id=%s", sensorId));
            throw new RuntimeException("iotdb: 查询最新状态失败，报错信息：" + e.getMessage());
        }
    }

    @Override
    public List<SensorStatus> fetchSensorAllStatus(String sensorId) {
        List<SensorStatus> res = new ArrayList<>();
        try {
            String sql = IotdbSensorStatusUtil.sqlToSelectSensorAllStatus(sensorId);
            session.open();
            SessionDataSet sessionDataSet = session.executeQueryStatement(sql);
            SessionDataSet.DataIterator dataIterator = sessionDataSet.iterator();
            dataIterator.next();


            while (dataIterator.next()) {
                SensorStatus status = new SensorStatus();
                status.setTimestamp(dataIterator.getTimestamp("Time"));
                status.setStatus(dataIterator.getString(IotdbSensorStatusUtil.getSensorStatusTimeSeries(sensorId) + ".status"));
                res.add(status);
            }

            session.close();
            log.info(String.format("iotdb: 查询所有状态成功！传感器id=%s，状态总数量: %d", sensorId, res.size()));
            return res;
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 查询所有状态失败，传感器id=%s", sensorId));
            throw new RuntimeException("iotdb: 查询所有状态失败，报错信息：" + e.getMessage());
        }
    }

    /**
     * 删除传感器某段时间内的状态
     * @param sensorId 传感器id
     */
    public void removeSensorStatusWithTime(String sensorId, String startTime, String endTime) {
        String sql = IotdbSensorStatusUtil.sqlToDeleteSensorAllStatus(sensorId);
        try {
            log.info("sql: " + sql);
            session.open();
            session.executeNonQueryStatement(sql);
            session.close();
            log.info(String.format("iotdb: 删除传感器状态成功！传感器id=%s，时间从%s到%s", sensorId, startTime, endTime));
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 删除传感器状态失败，传感器id=%s，时间从%s到%s", sensorId, startTime, endTime));
            throw new RuntimeException("iotdb: 删除传感器状态失败，报错信息：" + e.getMessage());
        }
    }

    /**
     * 删除传感器某段时间内的状态
     * @param sensorId 传感器id
     */
    public void removeSensorAllStatus(String sensorId) {
        String sql = IotdbSensorStatusUtil.sqlToDeleteSensorAllStatus(sensorId);
        try {
            log.info("sql: " + sql);
            session.open();
            session.executeNonQueryStatement(sql);
            session.close();
            log.info(String.format("iotdb: 删除传感器全部状态信息成功！传感器id=%s", sensorId));
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 删除传感器全部状态信息失败，传感器id=%s", sensorId));
            throw new RuntimeException("iotdb: 删除传感器全部状态信息失败，报错信息：" + e.getMessage());
        }
    }
}
