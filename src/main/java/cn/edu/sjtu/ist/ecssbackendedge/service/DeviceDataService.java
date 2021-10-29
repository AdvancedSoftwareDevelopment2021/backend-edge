package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;

public interface DeviceDataService {

    Response insertDeviceData(DeviceData deviceData);

    Response deleteDeviceData(Long id);

    Response updateDeviceData(Long id, DeviceData deviceData);

    Response getDeviceData(Long id);
}
