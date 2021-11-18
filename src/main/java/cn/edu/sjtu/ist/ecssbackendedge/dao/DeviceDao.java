package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.model.device.Device;

import java.util.List;

/**
 * @brief 设备Dao
 * @author rsp
 * @version 0.1
 * @date 2021-11-06
 */
public interface DeviceDao {

    void createDevice(Device device);

    void removeDevice(String id);

    void modifyDevice(Device device);

    Device findDeviceById(String id);

    List<Device> findDeviceByName(String name);

    List<Device> findAllDevices();
}
