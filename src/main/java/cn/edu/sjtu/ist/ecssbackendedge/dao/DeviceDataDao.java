package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device.DeviceData;

import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备数据Dao
 * @date 2021-11-06
 */
public interface DeviceDataDao {

    void saveDeviceData(String deviceId, String sensorName, String data);

    boolean createDeviceData(DeviceData deviceData);

    void removeDeviceDataById(String deviceId, String startTime, String endTime);

    boolean modifyDeviceData(DeviceData deviceData);

    DeviceData findLatestDeviceData(String deviceId, String sensorName);

    List<DeviceData> findDeviceHistoryDataWithLimit(String deviceId, String sensorName, String startTime, String endTime, int limit, int offset);

    List<DeviceData> findDeviceAllHistoryDataWithTime(String deviceId, String startTime, String endTime);

    List<DeviceData> findDeviceAllHistoryData(String deviceId, String sensorName);

//    List<DeviceData> findAllDeviceHistoryData(String deviceId, String startTime, String endTime);
}
