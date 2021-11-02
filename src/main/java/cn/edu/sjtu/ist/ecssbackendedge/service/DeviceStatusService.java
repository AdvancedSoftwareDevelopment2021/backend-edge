package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceStatusDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;

public interface DeviceStatusService {

    Response insertDeviceStatus(DeviceStatusDTO deviceStatusDTO);

    Response deleteDeviceStatus(String id);

    Response updateDeviceStatus(String id, DeviceStatusDTO deviceStatusDTO);

    Response getDeviceStatus(String id);

}
