package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceData;

import java.util.Date;

/**
 * @brief 设备数据Dao
 * @author rsp
 * @version 0.1
 * @date 2021-11-06
 */
public interface DeviceDataDao {

    boolean createDeviceData(DeviceData deviceData);

    void saveDeviceData(String deviceId, String data);

    void removeDeviceDataById(String id);

    boolean modifyDeviceData(DeviceData deviceData);

    DeviceData findDeviceDataById(String id);

}
