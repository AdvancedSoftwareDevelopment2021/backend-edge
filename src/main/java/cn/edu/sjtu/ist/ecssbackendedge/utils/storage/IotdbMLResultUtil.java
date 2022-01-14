package cn.edu.sjtu.ist.ecssbackendedge.utils.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author dyanjun
 * @date 2021/12/26 23:48
 */
@Slf4j
@Component
public class IotdbMLResultUtil {
    /**
     * 设备数据存储前缀
     */
    private static String MLResultPrefix;

    /**
     * 设置设备数据存储前缀
     *
     * @param MLResultPrefix 设备数据存储前缀
     */
    @Value("${iotdb.storage.device.ml.result}")
    public void setDeviceCommandStoragePrefix(String MLResultPrefix) {
        IotdbMLResultUtil.MLResultPrefix = MLResultPrefix;
    }

    /**
     * 获取单个设备的数据的时间序列
     *
     * @param deviceId 设备id
     */
    public static String getDeviceCommandTimeSeries(String deviceId) {
        return IotdbMLResultUtil.MLResultPrefix + "." + deviceId;
    }

    /**
     * sql根据设备id获取最近一次状态
     */
    public static String sqlToSelectLatestData(String deviceId, String driverName) {
        String timeSeries = getDeviceCommandTimeSeries(deviceId);
        String sql = String.format("select * from %s where driverName=\"%s\" order by time desc limit 1", timeSeries, driverName);
        log.info(sql);
        return sql;
    }

    /**
     * sql查询设备历史数据的条数
     *
     * @param deviceId  设备 id
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static String sqlToCountDeviceCommand(String deviceId, String startTime, String endTime) {
        String sql = String.format("select count(*) from %s", getDeviceCommandTimeSeries(deviceId));
        sql = String.format("%s where timestamp > %s and timestamp < %s", sql, startTime, endTime);
        log.info(sql);
        return sql;
    }

    /**
     * sql查询设备的所有Driver在某个时间区段的历史数据
     *
     * @param deviceId   设备 id
     * @param driverName 执行器名称
     */
    public static String sqlToSelectDeviceAllCommand(String deviceId, String driverName) {
        String sql = String.format("select * from %s", getDeviceCommandTimeSeries(deviceId));
        sql = String.format("%s where driverName=\"%s\"", sql, driverName);
        log.info(sql);
        return sql;
    }

    /**
     * sql查询设备的所有driver在某个时间区段的历史数据
     *
     * @param deviceId  设备 id
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static String sqlToSelectDeviceAllCommandWithTime(String deviceId, String startTime, String endTime) {
        String sql = String.format("select * from %s", getDeviceCommandTimeSeries(deviceId));
        sql = String.format("%s where timestamp > %s and timestamp < %s", sql, startTime, endTime);
        log.info(sql);
        return sql;
    }

    /**
     * sql查询设备的某个driver在某个时间区段的历史数据
     * 不是分页查询
     *
     * @param deviceId   设备 id
     * @param driverName driver名称
     * @param startTime  开始时间
     * @param endTime    结束时间
     */
    public static String sqlToSelectDeviceCommandWithDriverAndTime(String deviceId, String driverName, String startTime, String endTime) {
        String sql = String.format("select * from %s", getDeviceCommandTimeSeries(deviceId));
        sql = String.format("%s where driverName=\"%s\" and timestamp > %s and timestamp < %s", sql, driverName, startTime, endTime);
        log.info(sql);
        return sql;
    }

    /**
     * sql查询设备的某个driver在某个时间区段的历史数据
     * 分页查询，limit, offset
     *
     * @param deviceId   设备 id
     * @param driverName driver名称
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param limit      返回的行数
     * @param offset     偏移的行数
     */
    public static String sqlToSelectDeviceCommandWithLimit(String deviceId, String driverName,
                                                        String startTime, String endTime,
                                                        int limit, int offset) {
        String sql = String.format("select * from %s", getDeviceCommandTimeSeries(deviceId));
        String limitPart = String.format("limit %d offset %d", limit, offset);
        sql = String.format("%s where driverName=\"%s\" and timestamp > %s and timestamp < %s %s",
                sql, driverName, startTime, endTime, limitPart);
        log.info(sql);
        return sql;
    }

    /**
     * sql删除设备数据
     *
     * @param deviceId  设备 id
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static String sqlToDeleteDeviceCommand(String deviceId, String startTime, String endTime) {
        String sql = String.format("delete from %s", getDeviceCommandTimeSeries(deviceId));
        sql = String.format("%s where time > %s and time <= %s", sql, startTime, endTime);
        log.info(sql);
        return sql;
    }
}
