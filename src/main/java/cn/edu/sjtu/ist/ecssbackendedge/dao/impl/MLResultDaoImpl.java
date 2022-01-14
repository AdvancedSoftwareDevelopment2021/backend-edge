package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.MLResultDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.CommandData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.driver.DriverStatus;
import cn.edu.sjtu.ist.ecssbackendedge.utils.storage.IotdbMLResultUtil;
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
 * @author dyanjun
 * @date 2022/1/14 14:23
 */
@Slf4j
@Component
public class MLResultDaoImpl implements MLResultDao {

    @Autowired
    private Session session;

    @Autowired
    private SessionPool sessionPool;

    @Override
    public boolean saveMLResult(CommandData commandData) {
        String tableName = IotdbMLResultUtil.getDeviceCommandTimeSeries(commandData.getDeviceId());
        List<String> measurements = CommandData.getMeasurements();
        List<String> values = Arrays.asList(commandData.getDeviceId(), commandData.getDriverName(), commandData.getData(), commandData.getStatus().getStatus());
        try {
            sessionPool.insertRecord(tableName, (new Date()).getTime(), measurements, values);
            log.info(String.format("iotdb: 插入设备数据成功！表名=%s", tableName));
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 插入设备数据报错，设备id=%s, 控制器name=%s", commandData.getDeviceId(), commandData.getDriverName()));
            throw new RuntimeException("iotdb: 插入设备数据失败，报错信息：" + e.getMessage());
        }
        return true;
    }

    @Override
    public List<CommandData> findMLAllHistoryResult(String deviceId, String driverName) {
        List<CommandData> res = new ArrayList<>();
        try {
            String sql = IotdbMLResultUtil.sqlToSelectDeviceAllCommand(deviceId, driverName);
            session.open();
            SessionDataSet sessionDataSet = session.executeQueryStatement(sql);
            SessionDataSet.DataIterator dataIterator = sessionDataSet.iterator();

            while (dataIterator.next()) {
                CommandData commandData = new CommandData();
                commandData.setDeviceId(deviceId);
                commandData.setDriverName(driverName);
                commandData.setTimestamp(dataIterator.getTimestamp("Time"));
                commandData.setData(dataIterator.getString(IotdbMLResultUtil.getDeviceCommandTimeSeries(deviceId) + ".data"));
                commandData.setStatus(DriverStatus.fromString(dataIterator.getString(IotdbMLResultUtil.getDeviceCommandTimeSeries(deviceId) + ".status")));
                res.add(commandData);
            }
            session.close();
            log.info(String.format("iotdb: 查询所有历史状态成功，设备id=%s, driver名称=%s, 返回数据数量%d", deviceId, driverName, res.size()));
            return res;
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 查询所有历史状态失败，设备id=%s, driver名称=%s", deviceId, driverName));
            throw new RuntimeException("iotdb: 查询所有历史状态失败，报错信息：" + e.getMessage());
        }
    }

    @Override
    public List<CommandData> findMLResultWithTime(String deviceId, String driverName, String startTime, String endTime) {
        List<CommandData> res = new ArrayList<>();
        try {
            String sql = IotdbMLResultUtil.sqlToSelectDeviceCommandWithDriverAndTime(deviceId, driverName, startTime, endTime);
            session.open();
            SessionDataSet sessionDataSet = session.executeQueryStatement(sql);
            SessionDataSet.DataIterator dataIterator = sessionDataSet.iterator();
            while (dataIterator.next()) {
                CommandData commandData = new CommandData();
                commandData.setDeviceId(deviceId);
                commandData.setDriverName(driverName);
                commandData.setTimestamp(dataIterator.getTimestamp("Time"));
                commandData.setData(dataIterator.getString(IotdbMLResultUtil.getDeviceCommandTimeSeries(deviceId) + ".data"));
                commandData.setStatus(DriverStatus.fromString(dataIterator.getString(IotdbMLResultUtil.getDeviceCommandTimeSeries(deviceId) + ".status")));
                res.add(commandData);
            }
            session.close();
            log.info(String.format("iotdb: 查询所有历史状态成功，设备id=%s, driver名称=%s, 返回数据数量%d", deviceId, driverName, res.size()));
            return res;
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 查询所有历史状态失败，设备id=%s, driver名称=%s", deviceId, driverName));
            throw new RuntimeException("iotdb: 查询所有历史状态失败，报错信息：" + e.getMessage());
        }
    }
}
