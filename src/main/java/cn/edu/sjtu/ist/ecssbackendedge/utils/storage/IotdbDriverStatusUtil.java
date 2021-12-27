package cn.edu.sjtu.ist.ecssbackendedge.utils.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author dyanjun
 * @date 2021/12/27 0:26
 */
@Component
@Slf4j
public class IotdbDriverStatusUtil {
    /**
     * 控制器状态存储前缀
     */
    private static String DriverStatusStoragePrefix;

    /**
     * 设置控制器状态存储前缀
     * @param DriverStatusStoragePrefix 控制器状态存储前缀
     */
    @Value("${iotdb.storage.device.driver.status}")
    public void setDriverStatusStoragePrefix(String DriverStatusStoragePrefix) {
        IotdbDriverStatusUtil.DriverStatusStoragePrefix = DriverStatusStoragePrefix;
    }

    /**
     * 获取控制器状态的时间序列
     * @param DriverId 控制器 id
     */
    public static String getDriverStatusTimeSeries(String DriverId) {
        return IotdbDriverStatusUtil.DriverStatusStoragePrefix + "." + DriverId;
    }

    /**
     * sql根据控制器id获取最近一次状态
     */
    public static String sqlToSelectLatestStatus(String DriverId) {
        String sql = String.format("select * from %s order by time desc limit 1", getDriverStatusTimeSeries(DriverId));
        log.info(sql);
        return sql;
    }

    /**
     * sql查询控制器固定时段历史状态的条数
     * @param DriverId 控制器 id
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static String sqlToCountDriverStatus(String DriverId, String startTime, String endTime) {
        String sql = String.format("select count(*) from %s", getDriverStatusTimeSeries(DriverId));
        sql = String.format("%s where timestamp >= %s and timestamp <= %s", sql, startTime, endTime);
        log.info(sql);
        return sql;
    }


    /**
     * sql选择控制器固定时段的状态
     * @param DriverId 控制器 id
     */
    public static String sqlToSelectDriverAllStatus(String DriverId) {
        String sql = String.format("select * from %s", getDriverStatusTimeSeries(DriverId));
        log.info(sql);
        return sql;
    }

    /**
     * sql选择控制器固定时段的状态
     * @param DriverId 控制器 id
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static String sqlToSelectDriverStatus(String DriverId, String startTime, String endTime) {
        String sql = String.format("select * from %s", getDriverStatusTimeSeries(DriverId));
        sql = String.format("%s where timestamp >= %s and timestamp <= %s", sql, startTime, endTime);
        log.info(sql);
        return sql;
    }

    /**
     * sql查询控制器在某个时间区段的历史状态
     * @param DriverId 控制器 id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param limit     返回的行数
     * @param offset    偏移的行数
     */
    public static String sqlToSelectDriverStatusWithLimit(String DriverId,
                                                          String startTime, String endTime,
                                                          int limit, int offset) {
        String sql = String.format("select * from %s", getDriverStatusTimeSeries(DriverId));
        String limitPart = String.format("limit %d offset %d", limit, offset);
        sql = String.format("%s where timestamp >= %s and timestamp <= %s %s", sql, startTime, endTime, limitPart);
        log.info(sql);
        return sql;
    }

    /**
     * sql，删除控制器固定时段的状态
     * @param DriverId 控制器 id
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static String sqlToDeleteDriverStatus(String DriverId, String startTime, String endTime) {
        String sql = String.format("delete from %s", getDriverStatusTimeSeries(DriverId));
        sql = String.format("%s where timestamp >= %s and timestamp <= %s", sql, startTime, endTime);
        log.info(sql);
        return sql;
    }

    /**
     * sql，删除控制器的所有状态
     * @param DriverId 控制器 id
     */
    public static String sqlToDeleteDriverAllStatus(String DriverId) {
        String startTime = "1000-01-01 08:00:00";
        String endTime = "3000-01-01 08:00:00";
        return sqlToDeleteDriverStatus(DriverId, startTime, endTime);
    }
}
