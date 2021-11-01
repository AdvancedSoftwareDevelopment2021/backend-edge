package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.entity.ddo.DeviceProducedData;

public interface DeviceDataDao {

    boolean createDeviceData(DeviceProducedData deviceProducedData);

    void removeDeviceDataById(Long id);

    boolean modifyDeviceData(DeviceProducedData deviceProducedData);

    DeviceProducedData findDeviceDataById(Long id);
}
