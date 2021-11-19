package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceStatusDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceStatusIotdbPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceStatus;

import cn.edu.sjtu.ist.ecssbackendedge.utils.storage.IotdbDeviceStatusUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.Session;
import org.apache.iotdb.session.SessionDataSet;
import org.apache.iotdb.session.pool.SessionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@Profile("dev")
public class DeviceStatusDaoIotdbImpl implements DeviceStatusDao {

    @Autowired
    private Session session;

    @Autowired
    private SessionPool sessionPool;

    /**
     * 由sensor调用，插入设备的状态
     * @param deviceId 设备 id
     * @param sensorName sensor名称
     * @param status 状态
     */
    @Override
    public void saveDeviceStatus(String deviceId, String sensorName, String status) {
        String tableName = IotdbDeviceStatusUtil.getDeviceStatusTimeSeries(deviceId);
        List<String> measurements = DeviceStatusIotdbPO.getMeasurements();
        List<String> values = Arrays.asList(deviceId, sensorName, status);

        try {
            sessionPool.insertRecord(tableName, (new Date()).getTime(), measurements, values);
            log.info(String.format("iotdb: 插入状态成功！ 表名=%s", tableName));
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 插入状态数据失败，设备id=%s，状态=%s", deviceId, status));
            throw new RuntimeException("iotdb: 插入状态数据失败，报错信息：" + e.getMessage() );
        }
    }

    /**
     * 插入设备状态
     * @param deviceStatus 设备状态
     * @return true/false
     */
    @Override
    public boolean createDeviceStatus(DeviceStatus deviceStatus) {
        String tableName = IotdbDeviceStatusUtil.getDeviceStatusTimeSeries(deviceStatus.getDeviceId());
        List<String> measurements = DeviceStatusIotdbPO.getMeasurements();
        List<String> values = Arrays.asList(deviceStatus.getDeviceId(), deviceStatus.getSensorName(), deviceStatus.getStatus());
        try {
            sessionPool.insertRecord(tableName, deviceStatus.getTimestamp().getTime(), measurements, values);
            log.info(String.format("iotdb: 插入状态数据成功！表名=%s", tableName));
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 插入状态数据报错，设备id=%s, 状态=%s", deviceStatus.getDeviceId(), deviceStatus.getStatus()));
            throw new RuntimeException("iotdb: 插入状态数据失败，报错信息：" + e.getMessage());
        }
        return true;
    }

    /**
     * 删除设备某段时间内的状态
     * @param deviceId 设备id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    @Override
    public void removeDeviceStatusById(String deviceId, String startTime, String endTime) {
        String sql = IotdbDeviceStatusUtil.sqlToDeleteDeviceStatus(deviceId, startTime, endTime);
        try {
            log.info("sql: " + sql);
            session.open();
            session.executeNonQueryStatement(sql);
            session.close();
            log.info(String.format("iotdb: 删除设备状态成功！设备id=%s，时间从%s到%s", deviceId, startTime, endTime));
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 删除设备状态失败，设备id=%s，时间从%s到%s", deviceId, startTime, endTime));
            throw new RuntimeException("iotdb: 删除设备状态失败，报错信息：" + e.getMessage());
        }
    }

    /**
     * 更新设备状态（未实现，理论上不会被调用）
     * @param deviceStatus 设备状态
     * @return true/false
     */
    @Override
    public boolean modifyDeviceStatus(DeviceStatus deviceStatus) {
        return true;
    }

    /**
     * 查找最新的设备状态
     * @param deviceId 设备id
     * @param sensorName sensor名称
     * @return 最新的设备状态
     */
    @Override
    public DeviceStatus findLatestDeviceStatus(String deviceId, String sensorName) {
        DeviceStatus res = new DeviceStatus();
        try{
            String sql = IotdbDeviceStatusUtil.sqlToSelectLatestStatus(deviceId, sensorName);
            session.open();
            SessionDataSet sessionDataSet = session.executeQueryStatement(sql);
            session.close();

            SessionDataSet.DataIterator dataIterator = sessionDataSet.iterator();
            dataIterator.next(); // 跳过表头的属性项

            res.setDeviceId(deviceId);
            res.setSensorName(sensorName);
            res.setTimestamp(dataIterator.getTimestamp("Time"));
            res.setStatus(dataIterator.getString(IotdbDeviceStatusUtil.getDeviceStatusTimeSeries(deviceId) + ".status"));

            log.info(String.format("iotdb: 查询最新状态成功！设备id=%s, sensor名称=%s的最新状态: %s", deviceId,  sensorName, res));
            return res;
        }catch (IoTDBConnectionException | StatementExecutionException e){
            log.error(String.format("iotdb: 查询最新状态失败，设备id=%s, sensor名称=%s", deviceId, sensorName));
            throw new RuntimeException("iotdb: 查询最新状态失败，报错信息：" + e.getMessage());
        }
    }

    /**
     * 按条件查询某段时间内的状态
     * @param deviceId 设备id
     * @param sensorName sensor名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param limit 限制
     * @param offset 偏移量
     * @return 设备状态列表
     */
    @Override
    public List<DeviceStatus> findDeviceHistoryStatus(String deviceId, String sensorName, String startTime, String endTime, int limit, int offset) {
        List<DeviceStatus> res = new ArrayList<>();
        try{
            String sql = IotdbDeviceStatusUtil.sqlToSelectDeviceStatus(deviceId, sensorName, startTime, endTime);
            session.open();
            SessionDataSet sessionDataSet = session.executeQueryStatement(sql);
            session.close();

            SessionDataSet.DataIterator dataIterator = sessionDataSet.iterator();
            dataIterator.next();

            while (dataIterator.next()) {
                DeviceStatus status = new DeviceStatus();
                status.setDeviceId(deviceId);
                status.setSensorName(sensorName);
                status.setTimestamp(dataIterator.getTimestamp("Time"));
                status.setStatus(dataIterator.getString(IotdbDeviceStatusUtil.getDeviceStatusTimeSeries(deviceId) + ".status"));
                res.add(status);
            }
            log.info(String.format("iotdb: 查询所有历史数据失败，设备id=%s, sensor名称=%s, 返回状态数量%d", deviceId, sensorName, res.size()));
            return res;
        }catch (IoTDBConnectionException | StatementExecutionException e){
            log.error(String.format("iotdb: 查询所有历史数据失败，设备id=%s, sensor名称=%s", deviceId, sensorName));
            throw new RuntimeException("iotdb: 查询所有历史数据失败，报错信息：" + e.getMessage());
        }
    }
}
