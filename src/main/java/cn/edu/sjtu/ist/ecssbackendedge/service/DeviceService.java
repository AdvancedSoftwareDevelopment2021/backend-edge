package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;

public interface DeviceService {

    /**
     * 新增设备
     * @param deviceDTO 设备信息
     * @return response
     */
    Response insertDevice(DeviceDTO deviceDTO);

    Response deleteDevice(String id);

    Response updateDevice(String id, DeviceDTO deviceDTO);

    Response getDevice(String id);

    Response getDeviceAll();
}
