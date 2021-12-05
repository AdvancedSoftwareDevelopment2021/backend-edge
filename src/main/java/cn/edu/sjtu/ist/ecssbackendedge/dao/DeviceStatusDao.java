package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceStatus;

import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备状态Dao
 * @date 2021-11-06
 */
public interface DeviceStatusDao {

    void saveDeviceStatus(String deviceId, String sensorName, String status);

    boolean createDeviceStatus(DeviceStatus deviceStatus);

    void removeDeviceStatusById(String deviceId, String startTime, String endTime);

    boolean modifyDeviceStatus(DeviceStatus deviceStatus);

    DeviceStatus findLatestDeviceStatus(String deviceId, String sensorName);

    List<DeviceStatus> findDeviceHistoryStatus(String deviceId, String sensorName, String startTime, String endTime, int limit, int offset);

}
