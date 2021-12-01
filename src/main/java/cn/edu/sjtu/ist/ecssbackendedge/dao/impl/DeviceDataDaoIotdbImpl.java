package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceDataIotdbPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceData;
import cn.edu.sjtu.ist.ecssbackendedge.utils.storage.IotdbDeviceDataUtil;

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
 * @brief 设备数据DaoImpl
 * @author rsp
 * @version 0.1
 * @date 2021-11-19
 */
@Slf4j
@Component
public class DeviceDataDaoIotdbImpl implements DeviceDataDao {

    @Autowired
    private Session session;

    @Autowired
    private SessionPool sessionPool;

    /**
     * 由sensor调用，插入设备的数据
     * @param deviceId 设备 id
     * @param sensorName sensor名称
     * @param data 数据
     */
    @Override
    public void saveDeviceData(String deviceId, String sensorName, String data) {
        String tableName = IotdbDeviceDataUtil.getDeviceDataTimeSeries(deviceId);
        List<String> measurements = DeviceDataIotdbPO.getMeasurements();
        List<String> values = Arrays.asList(deviceId, sensorName, data);

        try {
            sessionPool.insertRecord(tableName, (new Date()).getTime(), measurements, values);
            log.info(String.format("iotdb: 插入设备数据成功！ 表名=%s", tableName));
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 插入设备数据失败，设备id=%s，数据=%s", deviceId, data));
            throw new RuntimeException("iotdb: 插入设备数据失败，报错信息：" + e.getMessage() );
        }
    }

    /**
     * 插入设备数据
     * @param deviceData 设备数据
     * @return true/false
     */
    @Override
    public boolean createDeviceData(DeviceData deviceData) {
        String tableName = IotdbDeviceDataUtil.getDeviceDataTimeSeries(deviceData.getDeviceId());
        List<String> measurements = DeviceDataIotdbPO.getMeasurements();
        List<String> values = Arrays.asList(deviceData.getDeviceId(), deviceData.getSensorName(), deviceData.getData());
        try {
            sessionPool.insertRecord(tableName, deviceData.getTimestamp().getTime(), measurements, values);
            log.info(String.format("iotdb: 插入设备数据成功！表名=%s", tableName));
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 插入设备数据报错，设备id=%s, 数据=%s", deviceData.getDeviceId(), deviceData.getData()));
            throw new RuntimeException("iotdb: 插入设备数据失败，报错信息：" + e.getMessage());
        }
        return true;
    }

    /**
     * 删除设备某段时间内的数据
     * @param deviceId 设备id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    @Override
    public void removeDeviceDataById(String deviceId, String startTime, String endTime) {
        String sql = IotdbDeviceDataUtil.sqlToDeleteDeviceData(deviceId, startTime, endTime);
        try {
            log.info("sql: " + sql);
            session.open();
            session.executeNonQueryStatement(sql);
            session.close();
            log.info(String.format("iotdb: 删除设备数据成功！设备id=%s，时间从%s到%s", deviceId, startTime, endTime));
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 删除设备数据失败，设备id=%s，时间从%s到%s", deviceId, startTime, endTime));
            throw new RuntimeException("iotdb: 删除设备数据失败，报错信息：" + e.getMessage());
        }
    }

    /**
     * 更新设备数据（未实现，理论上不会被调用）
     * @param deviceData 设备数据
     * @return true/false
     */
    @Override
    public boolean modifyDeviceData(DeviceData deviceData) {
        return true;
    }

    /**
     * 查找最新的设备数据
     * @param deviceId 设备id
     * @param sensorName sensor名称
     * @return 最新的设备数据
     */
    @Override
    public DeviceData findLatestDeviceData(String deviceId, String sensorName) {
        DeviceData res = new DeviceData();
        try{
            String sql = IotdbDeviceDataUtil.sqlToSelectLatestData(deviceId, sensorName);
            session.open();
            SessionDataSet sessionDataSet = session.executeQueryStatement(sql);
            SessionDataSet.DataIterator dataIterator = sessionDataSet.iterator();
            dataIterator.next();

            res.setDeviceId(deviceId);
            res.setSensorName(sensorName);
            res.setTimestamp(dataIterator.getTimestamp("Time"));
            res.setData(dataIterator.getString(IotdbDeviceDataUtil.getDeviceDataTimeSeries(deviceId) + ".data"));

            session.close();
            log.info(String.format("iotdb: 查询最新数据成功！设备id=%s, sensor名称=%s的最新数据: %s", deviceId,  sensorName, res));
            return res;
        }catch (IoTDBConnectionException | StatementExecutionException e){
            log.error(String.format("iotdb: 查询最新数据失败，设备id=%s, sensor名称=%s", deviceId, sensorName));
            throw new RuntimeException("iotdb: 查询最新数据失败，报错信息：" + e.getMessage());
        }
    }

    /**
     * 查询设备的某个sensor在某个时间区段的历史数据
     * 分页查询，limit, offset
     * @param deviceId 设备id
     * @param sensorName sensor名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param limit 限制
     * @param offset 偏移量
     * @return 设备数据列表
     */
    @Override
    public List<DeviceData> findDeviceHistoryDataWithLimit(String deviceId, String sensorName, String startTime, String endTime, int limit, int offset) {
        List<DeviceData> res = new ArrayList<>();
        try{
            String sql = IotdbDeviceDataUtil.sqlToSelectDeviceDataWithLimit(deviceId, sensorName, startTime, endTime, limit, offset);
            session.open();
            SessionDataSet sessionDataSet = session.executeQueryStatement(sql);
            SessionDataSet.DataIterator dataIterator = sessionDataSet.iterator();
            while (dataIterator.next()) {
                DeviceData data = new DeviceData();
                data.setDeviceId(deviceId);
                data.setSensorName(sensorName);
                data.setTimestamp(dataIterator.getTimestamp("Time"));
                data.setData(dataIterator.getString(IotdbDeviceDataUtil.getDeviceDataTimeSeries(deviceId) + ".data"));
                res.add(data);
            }
            session.close();
            log.info(String.format("iotdb: 查询所有历史数据成功，设备id=%s, sensor名称=%s, 返回数据数量%d", deviceId, sensorName, res.size()));
            return res;
        }catch (IoTDBConnectionException | StatementExecutionException e){
            log.error(String.format("iotdb: 查询所有历史数据失败，设备id=%s, sensor名称=%s", deviceId, sensorName));
            throw new RuntimeException("iotdb: 查询所有历史数据失败，报错信息：" + e.getMessage());
        }
    }

    /**
     * 查询设备的所有sensor在某个时间区段的历史数据
     * @param deviceId 设备id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 设备数据列表
     */
    @Override
    public List<DeviceData> findDeviceAllHistoryDataWithTime(String deviceId, String startTime, String endTime) {
        List<DeviceData> res = new ArrayList<>();
        try{
            String sql = IotdbDeviceDataUtil.sqlToSelectDeviceAllDataWithTime(deviceId, startTime, endTime);
            session.open();
            SessionDataSet sessionDataSet = session.executeQueryStatement(sql);
            SessionDataSet.DataIterator dataIterator = sessionDataSet.iterator();
            while (dataIterator.next()) {
                DeviceData data = new DeviceData();
                data.setDeviceId(deviceId);
                data.setTimestamp(dataIterator.getTimestamp("Time"));
                data.setSensorName(dataIterator.getString(IotdbDeviceDataUtil.getDeviceDataTimeSeries(deviceId) + ".sensorName"));
                data.setData(dataIterator.getString(IotdbDeviceDataUtil.getDeviceDataTimeSeries(deviceId) + ".data"));
                res.add(data);
            }
            session.close();
            log.info(String.format("iotdb: 查询所有历史数据成功，设备id=%s, 返回数据数量%d", deviceId, res.size()));
            return res;
        }catch (IoTDBConnectionException | StatementExecutionException e){
            log.error(String.format("iotdb: 查询所有历史数据失败，设备id=%s", deviceId));
            throw new RuntimeException("iotdb: 查询所有历史数据失败，报错信息：" + e.getMessage());
        }
    }

    /**
     * 查询设备的所有sensor在某个时间区段的历史数据
     * @param deviceId 设备id
     * @param sensorName 传感器名称
     * @return 设备数据列表
     */
    @Override
    public List<DeviceData> findDeviceAllHistoryData(String deviceId, String sensorName) {
        List<DeviceData> res = new ArrayList<>();
        try{
            String sql = IotdbDeviceDataUtil.sqlToSelectDeviceAllData(deviceId, sensorName);
            session.open();
            SessionDataSet sessionDataSet = session.executeQueryStatement(sql);
            SessionDataSet.DataIterator dataIterator = sessionDataSet.iterator();
            while (dataIterator.next()) {
                DeviceData data = new DeviceData();
                data.setDeviceId(deviceId);
                data.setTimestamp(dataIterator.getTimestamp("Time"));
                data.setSensorName(dataIterator.getString(IotdbDeviceDataUtil.getDeviceDataTimeSeries(deviceId) + ".sensorName"));
                data.setData(dataIterator.getString(IotdbDeviceDataUtil.getDeviceDataTimeSeries(deviceId) + ".data"));
                res.add(data);
            }
            session.close();
            log.info(String.format("iotdb: 查询所有历史数据成功，设备id=%s, 返回数据数量%d", deviceId, res.size()));
            return res;
        }catch (IoTDBConnectionException | StatementExecutionException e){
            log.error(String.format("iotdb: 查询所有历史数据失败，设备id=%s", deviceId));
            throw new RuntimeException("iotdb: 查询所有历史数据失败，报错信息：" + e.getMessage());
        }
    }
}
