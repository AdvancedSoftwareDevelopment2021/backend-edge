package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceStatusDTO;

import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备状态service
 * @date 2021-11-08
 */
public interface DeviceStatusService {

    void insertDeviceStatus(DeviceStatusDTO deviceStatusDTO);

    void deleteDeviceHistoryStatus(String deviceId, String sensorName, String startTime, String endTime);

    void deleteDeviceAllHistoryStatus(String deviceId);

    void updateDeviceStatus(String id, DeviceStatusDTO deviceStatusDTO);

    DeviceStatusDTO getLatestDeviceStatus(String deviceId, String sensorName);

    List<DeviceStatusDTO> getDeviceHistoryStatus(String deviceId, String sensorName, String filters, int pageIndex, int pageSize);

}
