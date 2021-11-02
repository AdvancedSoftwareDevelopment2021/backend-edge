package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDataDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;

public interface DeviceDataService {

    Response insertDeviceData(DeviceDataDTO deviceDataDTO);

    Response deleteDeviceData(Long id);

    Response updateDeviceData(Long id, DeviceDataDTO deviceDataDTO);

    Response getDeviceData(Long id);
}
