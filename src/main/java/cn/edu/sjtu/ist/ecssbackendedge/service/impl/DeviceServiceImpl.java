package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.model.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceService;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;
import cn.edu.sjtu.ist.ecssbackendedge.utils.MessageProtocol;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public Response insertDevice(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setName(deviceDTO.getName());
        device.setModel(deviceDTO.getModel());
        log.info(Objects.requireNonNull(MessageProtocol.fromString(deviceDTO.getMessageProtocol())).getProtocol());
        device.setMessageProtocol(MessageProtocol.fromString(deviceDTO.getMessageProtocol()));
        deviceDao.createDevice(device);
        return new Response(200, "OK", "insert ok!");
    }

    @Override
    public Response deleteDevice(Long id) {
        deviceDao.removeDevice(id);
        return new Response(200, "OK", "delete ok!");
    }

    @Override
    public Response updateDevice(Long id, DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setId(id);
        device.setName(deviceDTO.getName());
        device.setModel(deviceDTO.getModel());
        device.setMessageProtocol(MessageProtocol.fromString(deviceDTO.getMessageProtocol()));
        deviceDao.modifyDevice(device);
        return new Response(200, "OK", "update ok!");
    }

    @Override
    public Response getDevice(Long id) {
        Device device = deviceDao.findDeviceById(id);
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(device.getId());
        deviceDTO.setName(device.getName());
        deviceDTO.setModel(device.getModel());
        deviceDTO.setMessageProtocol(device.getMessageProtocol().getProtocol());
        return new Response(200, "OK", device);
    }
}
