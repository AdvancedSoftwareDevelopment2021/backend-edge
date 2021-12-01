package cn.edu.sjtu.ist.ecssbackendedge.utils.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IotdbDeviceDataUtil {

    /**
     * 设备数据存储前缀
     */
    private static String deviceDataStoragePrefix;

    /**
     * 设置设备数据存储前缀
     * @param deviceDataStoragePrefix 设备数据存储前缀
     */
    @Value("${iotdb.storage.data.name}")
    public void setDeviceDataStoragePrefix(String deviceDataStoragePrefix) {
        IotdbDeviceDataUtil.deviceDataStoragePrefix = deviceDataStoragePrefix;
    }

    /**
     * 获取单个设备的数据的时间序列
     * @param deviceId 设备id
     */
    public static String getDeviceDataTimeSeries(String deviceId) {
        return IotdbDeviceDataUtil.deviceDataStoragePrefix + "." + deviceId;
    }

    /**
     * sql根据设备id获取最近一次状态
     */
    public static String sqlToSelectLatestData(String deviceId, String sensorName) {
        String timeSeries = getDeviceDataTimeSeries(deviceId);
        String sql = String.format("select * from %s where sensorName=\"%s\" order by time desc limit 1", timeSeries, sensorName);
        log.info(sql);
        return sql;
    }

    /**
     * sql查询设备历史数据的条数
     * @param deviceId 设备 id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public static String sqlToCountDeviceData(String deviceId, String startTime, String endTime) {
        String sql = String.format("select count(*) from %s", getDeviceDataTimeSeries(deviceId));
        sql = String.format("%s where timestamp > %s and timestamp < %s", sql, startTime, endTime);
        log.info(sql);
        return sql;
    }

    /**
     * sql查询设备的所有sensor在某个时间区段的历史数据
     * @param deviceId 设备 id
     * @param sensorName 传感器名称
     */
    public static String sqlToSelectDeviceAllData(String deviceId, String sensorName) {
        String sql = String.format("select * from %s", getDeviceDataTimeSeries(deviceId));
        sql = String.format("%s where sensorName=\"%s\"", sql, sensorName);
        log.info(sql);
        return sql;
    }

    /**
     * sql查询设备的所有sensor在某个时间区段的历史数据
     * @param deviceId 设备 id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public static String sqlToSelectDeviceAllDataWithTime(String deviceId, String startTime, String endTime) {
        String sql = String.format("select * from %s", getDeviceDataTimeSeries(deviceId));
        sql = String.format("%s where timestamp > %s and timestamp < %s", sql, startTime, endTime);
        log.info(sql);
        return sql;
    }

    /**
     * sql查询设备的某个sensor在某个时间区段的历史数据
     * 不是分页查询
     * @param deviceId 设备 id
     * @param sensorName sensor名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public static String sqlToSelectDeviceDataWithSensorAndTime(String deviceId, String sensorName, String startTime, String endTime) {
        String sql = String.format("select * from %s", getDeviceDataTimeSeries(deviceId));
        sql = String.format("%s where sensorName=\"%s\" and timestamp > %s and timestamp < %s", sql, sensorName, startTime, endTime);
        log.info(sql);
        return sql;
    }

    /**
     * sql查询设备的某个sensor在某个时间区段的历史数据
     * 分页查询，limit, offset
     * @param deviceId 设备 id
     * @param sensorName sensor名称
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param limit 返回的行数
     * @param offset 偏移的行数
     */
    public static String sqlToSelectDeviceDataWithLimit(String deviceId, String sensorName,
                                                          String startTime, String endTime,
                                                          int limit, int offset) {
        String sql = String.format("select * from %s", getDeviceDataTimeSeries(deviceId));
        String limitPart = String.format("limit %d offset %d", limit, offset);
        sql = String.format("%s where sensorName=\"%s\" and timestamp > %s and timestamp < %s %s",
                            sql, sensorName, startTime, endTime, limitPart);
        log.info(sql);
        return sql;
    }

    /**
     * sql删除设备数据
     * @param deviceId 设备 id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public static String sqlToDeleteDeviceData(String deviceId, String startTime, String endTime) {
        String sql = String.format("delete from %s", getDeviceDataTimeSeries(deviceId));
        sql = String.format("%s where time > %s and time <= %s", sql, startTime, endTime);
        log.info(sql);
        return sql;
    }
}
