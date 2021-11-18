package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;

/**
 * @brief 设备service
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
public interface DeviceService {

    Response insertDevice(DeviceDTO deviceDTO);

    Response deleteDevice(String id);

    Response updateDevice(String id, DeviceDTO deviceDTO);

    Response getDevice(String id);

    Response getAllDevices();
}
