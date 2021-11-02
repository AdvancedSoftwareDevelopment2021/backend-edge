package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.model.Device;

import java.util.List;

public interface DeviceDao {

    void createDevice(Device device);

    void removeDevice(String id);

    void modifyDevice(Device device);

    Device findDeviceById(String id);

    List<Device> findDeviceByName(String name);
}
