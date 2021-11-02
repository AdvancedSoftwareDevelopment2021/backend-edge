package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.model.DeviceStatus;

public interface DeviceStatusDao {

    boolean createDeviceStatus(DeviceStatus deviceStatus);

    void removeDeviceStatusById(String id);

    boolean modifyDeviceStatus(DeviceStatus deviceStatus);

    DeviceStatus findDeviceStatusById(String id);

}
