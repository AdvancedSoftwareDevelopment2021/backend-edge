package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;

public interface DeviceService {

    /**
     * 新增设备
     * @param device 设备信息
     * @return response
     */
    Response insertDevice(Device device);

    Response deleteDevice(Long id);

    Response updateDevice(Long id, Device device);

    Response getDevice(Long id);
}
