package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.entity.ddo.DeviceOwnedStatus;

public interface DeviceStatusDao {

    boolean createDeviceStatus(DeviceOwnedStatus deviceOwnedStatus);

    void removeDeviceStatusById(Long id);

    boolean modifyDeviceStatus(DeviceOwnedStatus deviceOwnedStatus);

    DeviceOwnedStatus findDeviceStatusById(Long id);

}
