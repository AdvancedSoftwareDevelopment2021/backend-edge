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

    Response deleteDeviceHistoryStatus(String deviceId, String sensorName, String startTime, String endTime);

    Response deleteDeviceAllHistoryStatus(String deviceId);

    Response updateDeviceStatus(String id, DeviceStatusDTO deviceStatusDTO);

    Response getLatestDeviceStatus(String deviceId, String sensorName);

    Response getDeviceHistoryStatus(String deviceId, String sensorName, String filters, int pageIndex, int pageSize);

}
