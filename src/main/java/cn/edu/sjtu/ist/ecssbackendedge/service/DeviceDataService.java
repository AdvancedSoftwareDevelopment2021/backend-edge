package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.device.DeviceDataDTO;

import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备数据service
 * @date 2021-11-08
 */
public interface DeviceDataService {

    void insertDeviceData(DeviceDataDTO deviceDataDTO);

    void deleteDeviceHistoryData(String deviceId, String sensorName, String startTime, String endTime);

    void deleteDeviceAllHistoryData(String deviceId);

    void updateDeviceData(String id, DeviceDataDTO deviceDataDTO);

    DeviceDataDTO getLatestDeviceData(String deviceId, String sensorName);

    List<DeviceDataDTO> getDeviceHistoryData(String deviceId, String sensorName, String filters, int pageIndex, int pageSize);

    List<DeviceDataDTO> getDeviceAllHistoryData(String deviceId, String sensorName);
}
