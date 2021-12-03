package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDataDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;

import java.util.List;

/**
 * @brief 设备数据service
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
public interface DeviceDataService {

    Response insertDeviceData(DeviceDataDTO deviceDataDTO);

    Response deleteDeviceHistoryData(String deviceId, String sensorName, String startTime, String endTime);

    Response deleteDeviceAllHistoryData(String deviceId);

    Response updateDeviceData(String id, DeviceDataDTO deviceDataDTO);

    Response getLatestDeviceData(String deviceId, String sensorName);

    Response getDeviceHistoryData(String deviceId, String sensorName, String filters, int pageIndex, int pageSize);

    Result<List<DeviceDataDTO>> getDeviceAllHistoryData(String deviceId, String sensorName);
}
