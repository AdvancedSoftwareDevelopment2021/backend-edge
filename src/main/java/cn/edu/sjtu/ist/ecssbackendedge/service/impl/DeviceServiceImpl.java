package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.model.device.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceService;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;

import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DeviceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @brief 设备serviceImpl
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceUtil deviceUtil;

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public Response insertDevice(DeviceDTO deviceDTO) {
        Device device = deviceUtil.convertDTO2Domain(deviceDTO);
        deviceDao.createDevice(device);
        Device device1 = deviceDao.findDeviceByName(device.getName()).get(0);
        if (device1 == null) {
            return new Response(400, "插入设备失败!", null);
        } else {
            return new Response(200, "插入设备成功!", deviceUtil.convertDomain2DTO(device1));
        }
    }

    @Override
    public Response deleteDevice(String id) {
        deviceDao.removeDevice(id);
        return new Response(200, "删除设备id=" + id + "成功!", null);
    }

    @Override
    public Response updateDevice(String id, DeviceDTO deviceDTO) {
        deviceDTO.setId(id);
        Device device = deviceUtil.convertDTO2Domain(deviceDTO);
        deviceDao.modifyDevice(device);
        return new Response(200, "更新设备id=" + id + "成功!", null);
    }

    @Override
    public Response getDevice(String id) {
        Device device = deviceDao.findDeviceById(id);
        DeviceDTO deviceDTO = deviceUtil.convertDomain2DTO(device);
        return new Response(200, "获取设备id=" + id + "成功!", deviceDTO);
    }

    @Override
    public Response getAllDevices() {
        List<Device> devices = deviceDao.findAllDevices();
        List<DeviceDTO> res = new ArrayList<>();
        for (Device device: devices) {
            DeviceDTO dto = deviceUtil.convertDomain2DTO(device);
            res.add(dto);
        }
        return new Response(200, "获取所有设备成功", res);
    }
}
