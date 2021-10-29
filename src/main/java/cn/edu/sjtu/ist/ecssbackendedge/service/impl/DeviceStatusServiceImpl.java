package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceStatusDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceStatus;
import cn.edu.sjtu.ist.ecssbackendedge.entity.ddo.DeviceOwnedStatus;
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
    public Response insertDeviceStatus(DeviceStatus deviceStatus) {
        DeviceOwnedStatus deviceOwnedStatus = new DeviceOwnedStatus();
        deviceOwnedStatus.setDeviceId(deviceStatus.getDeviceId());
        deviceOwnedStatus.setTimestamp(deviceStatus.getTimestamp());
        deviceOwnedStatus.setStatus(deviceStatus.getStatus());

        boolean ret = deviceStatusDao.createDeviceStatus(deviceOwnedStatus);
        if (ret) {
            return new Response(200, "OK", "insert device status ok!");
        } else {
            return new Response(400, "ERROR", "insert device status error! device id=" + deviceOwnedStatus.getDeviceId() + " not exists!");
        }
    }

    @Override
    public Response deleteDeviceStatus(Long id) {
        deviceStatusDao.removeDeviceStatusById(id);
        return new Response(200, "OK", "delete device status ok!");
    }

    @Override
    public Response updateDeviceStatus(Long id, DeviceStatus deviceStatus) {
        DeviceOwnedStatus deviceOwnedStatus = new DeviceOwnedStatus();
        deviceOwnedStatus.setId(id);
        deviceOwnedStatus.setDeviceId(deviceStatus.getDeviceId());
        deviceOwnedStatus.setTimestamp(deviceStatus.getTimestamp());
        deviceOwnedStatus.setStatus(deviceStatus.getStatus());

        boolean ret = deviceStatusDao.modifyDeviceStatus(deviceOwnedStatus);
        if (ret) {
            return new Response(200, "OK", "update device status ok!");
        } else {
            return new Response(400, "ERROR", "update device status error! device id=" + deviceOwnedStatus.getDeviceId() + " not exists!");
        }
    }

    @Override
    public Response getDeviceStatus(Long id) {
        DeviceOwnedStatus deviceOwnedStatus = deviceStatusDao.findDeviceStatusById(id);
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setId(deviceOwnedStatus.getId());
        deviceStatus.setDeviceId(deviceOwnedStatus.getDeviceId());
        deviceStatus.setTimestamp(deviceOwnedStatus.getTimestamp());
        deviceStatus.setStatus(deviceOwnedStatus.getStatus());
        return new Response(200, "OK", deviceStatus);
    }
}
