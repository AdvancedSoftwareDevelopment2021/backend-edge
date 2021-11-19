package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceStatusDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;

/**
 * @brief 设备状态service
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
public interface DeviceStatusService {

    Response insertDeviceStatus(DeviceStatusDTO deviceStatusDTO);

    Response deleteDeviceStatus(String id);

    Response updateDeviceStatus(String id, DeviceStatusDTO deviceStatusDTO);

    Response getDeviceStatus(String id);

}
