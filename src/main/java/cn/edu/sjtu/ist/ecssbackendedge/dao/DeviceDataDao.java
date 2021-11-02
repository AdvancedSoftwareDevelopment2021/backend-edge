package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.model.DeviceData;

public interface DeviceDataDao {

    boolean createDeviceData(DeviceData deviceData);

    void removeDeviceDataById(String id);

    boolean modifyDeviceData(DeviceData deviceData);

    DeviceData findDeviceDataById(String id);
}
