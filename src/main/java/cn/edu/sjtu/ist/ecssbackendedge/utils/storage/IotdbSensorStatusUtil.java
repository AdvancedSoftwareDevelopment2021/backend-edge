package cn.edu.sjtu.ist.ecssbackendedge.utils.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IotdbSensorStatusUtil {

    /**
     * 传感器状态存储前缀
     */
    private static String sensorStatusStoragePrefix;

    /**
     * 设置传感器状态存储前缀
     * @param sensorStatusStoragePrefix 传感器状态存储前缀
     */
    @Value("${iotdb.storage.device.sensor.status}")
    public void setSensorStatusStoragePrefix(String sensorStatusStoragePrefix) {
        IotdbSensorStatusUtil.sensorStatusStoragePrefix = sensorStatusStoragePrefix;
    }

    /**
     * 获取传感器状态的时间序列
     * @param sensorId 传感器 id
     */
    public static String getSensorStatusTimeSeries(String sensorId) {
        return IotdbSensorStatusUtil.sensorStatusStoragePrefix + "." + sensorId;
    }

    /**
     * sql根据传感器id获取最近一次状态
     */
    public static String sqlToSelectLatestStatus(String sensorId) {
        String sql = String.format("select * from %s order by time desc limit 1", getSensorStatusTimeSeries(sensorId));
        log.info(sql);
        return sql;
    }

    /**
     * sql查询传感器固定时段历史状态的条数
     * @param sensorId 传感器 id
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static String sqlToCountSensorStatus(String sensorId, String startTime, String endTime) {
        String sql = String.format("select count(*) from %s", getSensorStatusTimeSeries(sensorId));
        sql = String.format("%s where timestamp >= %s and timestamp <= %s", sql, startTime, endTime);
        log.info(sql);
        return sql;
    }


    /**
     * sql选择传感器固定时段的状态
     * @param sensorId 传感器 id
     */
    public static String sqlToSelectSensorAllStatus(String sensorId) {
        String sql = String.format("select * from %s", getSensorStatusTimeSeries(sensorId));
        log.info(sql);
        return sql;
    }

    /**
     * sql选择传感器固定时段的状态
     * @param sensorId 传感器 id
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static String sqlToSelectSensorStatus(String sensorId, String startTime, String endTime) {
        String sql = String.format("select * from %s", getSensorStatusTimeSeries(sensorId));
        sql = String.format("%s where timestamp >= %s and timestamp <= %s", sql, startTime, endTime);
        log.info(sql);
        return sql;
    }

    /**
     * sql查询传感器在某个时间区段的历史状态
     * @param sensorId 传感器 id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param limit     返回的行数
     * @param offset    偏移的行数
     */
    public static String sqlToSelectSensorStatusWithLimit(String sensorId,
                                                          String startTime, String endTime,
                                                          int limit, int offset) {
        String sql = String.format("select * from %s", getSensorStatusTimeSeries(sensorId));
        String limitPart = String.format("limit %d offset %d", limit, offset);
        sql = String.format("%s where timestamp >= %s and timestamp <= %s %s", sql, startTime, endTime, limitPart);
        log.info(sql);
        return sql;
    }

    /**
     * sql，删除传感器固定时段的状态
     * @param sensorId 传感器 id
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static String sqlToDeleteSensorStatus(String sensorId, String startTime, String endTime) {
        String sql = String.format("delete from %s", getSensorStatusTimeSeries(sensorId));
        sql = String.format("%s where timestamp >= %s and timestamp <= %s", sql, startTime, endTime);
        log.info(sql);
        return sql;
    }

    /**
     * sql，删除传感器的所有状态
     * @param sensorId 传感器 id
     */
    public static String sqlToDeleteSensorAllStatus(String sensorId) {
        String startTime = "1000-01-01 08:00:00";
        String endTime = "3000-01-01 08:00:00";
        return sqlToDeleteSensorStatus(sensorId, startTime, endTime);
    }
}
