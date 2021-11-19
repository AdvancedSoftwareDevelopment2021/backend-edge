package cn.edu.sjtu.ist.ecssbackendedge.utils.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("dev")
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
     * 获取设备数据的时间序列
     * @param deviceId 设备id
     */
    public static String getDeviceDataTimeSeries(String deviceId) {
        return IotdbDeviceDataUtil.deviceDataStoragePrefix + deviceId;
    }

    /**
     * sql选择设备数据
     * @param deviceId 设备 id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    public static String sqlToSelectDeviceData(String deviceId, String sensorName, String startTime, String endTime) {
        String sql = String.format("select * from %s", getDeviceDataTimeSeries(deviceId));
        sql = String.format("%s where sensorName=\"%s\" and timestamp > %s and timestamp < %s", sql, sensorName, startTime, endTime);
        log.info(sql);
        return sql;
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
     * sql查询设备在某个时间区段的历史状态
     * @param deviceId 设备 id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param limit 返回的行数
     * @param offset 偏移的行数
     */
    public static String sqlToSelectDeviceDataWithLimit(String deviceId,
                                                          String startTime, String endTime,
                                                          int limit, int offset) {
        String sql = String.format("select * from %s", getDeviceDataTimeSeries(deviceId));
        String limitPart = String.format("limit %d offset %d", limit, offset);
        sql = String.format("%s where timestamp > %s and timestamp < %s %s", sql, startTime, endTime, limitPart);
        log.info(sql);
        return sql;
    }

    /**
     * sql删除设备状态
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
