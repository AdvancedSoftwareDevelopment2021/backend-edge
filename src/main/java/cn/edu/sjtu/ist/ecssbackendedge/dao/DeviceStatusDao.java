package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceStatus;

/**
 * @brief 设备状态Dao
 * @author rsp
 * @version 0.1
 * @date 2021-11-06
 */
public interface DeviceStatusDao {

    boolean createDeviceStatus(DeviceStatus deviceStatus);

    void removeDeviceStatusById(String id);

    boolean modifyDeviceStatus(DeviceStatus deviceStatus);

    DeviceStatus findDeviceStatusById(String id);

}
