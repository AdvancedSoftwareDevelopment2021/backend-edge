package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceStatus;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;

public interface DeviceStatusService {

    Response insertDeviceStatus(DeviceStatus deviceStatus);

    Response deleteDeviceStatus(Long id);

    Response updateDeviceStatus(Long id, DeviceStatus deviceStatus);

    Response getDeviceStatus(Long id);

}
