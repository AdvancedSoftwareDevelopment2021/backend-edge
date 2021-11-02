package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceStatusDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceStatusDTO;
import cn.edu.sjtu.ist.ecssbackendedge.model.DeviceStatus;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceStatusService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service
public class DeviceStatusServiceImpl implements DeviceStatusService {

    @Autowired
    private DeviceStatusDao deviceStatusDao;

    @Override
    public Response insertDeviceStatus(DeviceStatusDTO deviceStatusDTO) {
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setDeviceId(deviceStatusDTO.getDeviceId());
        deviceStatus.setTimestamp(deviceStatusDTO.getTimestamp());
        deviceStatus.setStatus(deviceStatusDTO.getStatus());

        boolean ret = deviceStatusDao.createDeviceStatus(deviceStatus);
        if (ret) {
            return new Response(200, "OK", "insert device status ok!");
        } else {
            return new Response(400, "ERROR", "insert device status error! device id=" + deviceStatus.getDeviceId() + " not exists!");
        }
    }

    @Override
    public Response deleteDeviceStatus(String id) {
        deviceStatusDao.removeDeviceStatusById(id);
        return new Response(200, "OK", "delete device status ok!");
    }

    @Override
    public Response updateDeviceStatus(String id, DeviceStatusDTO deviceStatusDTO) {
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setId(id);
        deviceStatus.setDeviceId(deviceStatusDTO.getDeviceId());
        deviceStatus.setTimestamp(deviceStatusDTO.getTimestamp());
        deviceStatus.setStatus(deviceStatusDTO.getStatus());

        boolean ret = deviceStatusDao.modifyDeviceStatus(deviceStatus);
        if (ret) {
            return new Response(200, "OK", "update device status ok!");
        } else {
            return new Response(400, "ERROR", "update device status error! device id=" + deviceStatus.getDeviceId() + " not exists!");
        }
    }

    @Override
    public Response getDeviceStatus(String id) {
        DeviceStatus deviceStatus = deviceStatusDao.findDeviceStatusById(id);
        DeviceStatusDTO deviceStatusDTO = new DeviceStatusDTO();
        deviceStatusDTO.setId(deviceStatus.getId());
        deviceStatusDTO.setDeviceId(deviceStatus.getDeviceId());
        deviceStatusDTO.setTimestamp(deviceStatus.getTimestamp());
        deviceStatusDTO.setStatus(deviceStatus.getStatus());
        return new Response(200, "OK", deviceStatusDTO);
    }
}
