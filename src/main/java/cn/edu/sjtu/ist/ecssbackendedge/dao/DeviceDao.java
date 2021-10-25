package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.entity.ddo.DeviceModel;

import java.util.List;

public interface DeviceDao {

    void createDevice(DeviceModel device);

    void removeDevice(Long id);

    void modifyDevice(DeviceModel device);

    DeviceModel findDeviceById(Long id);

    List<DeviceModel> findDeviceByName(String name);
}
