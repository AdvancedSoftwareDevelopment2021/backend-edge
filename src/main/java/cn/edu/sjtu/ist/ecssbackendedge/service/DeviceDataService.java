package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDataDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;

/**
 * @brief 设备数据service
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
public interface DeviceDataService {

    Response insertDeviceData(DeviceDataDTO deviceDataDTO);

    Response deleteDeviceData(String id);

    Response updateDeviceData(String id, DeviceDataDTO deviceDataDTO);

    Response getDeviceData(String id);
}
