package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceData;

import java.util.Date;
import java.util.List;

/**
 * @brief 设备数据Dao
 * @author rsp
 * @version 0.1
 * @date 2021-11-06
 */
public interface DeviceDataDao {

    void saveDeviceData(String deviceId, String sensorName, String data);

    boolean createDeviceData(DeviceData deviceData);

    void removeDeviceDataById(String deviceId, String startTime, String endTime);

    boolean modifyDeviceData(DeviceData deviceData);

    DeviceData findLatestDeviceData(String deviceId, String sensorName);

    List<DeviceData> findDeviceHistoryData(String deviceId, String sensorName, String startTime, String endTime, int limit, int offset);

}
