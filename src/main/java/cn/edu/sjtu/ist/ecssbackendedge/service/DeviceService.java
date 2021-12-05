package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDTO;

import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备service
 * @date 2021-11-08
 */
public interface DeviceService {

    DeviceDTO insertDevice(DeviceDTO deviceDTO);

    void deleteDevice(String id);

    DeviceDTO updateDevice(String id, DeviceDTO deviceDTO);

    DeviceDTO getDevice(String id);

    List<DeviceDTO> getAllDevices();
}
