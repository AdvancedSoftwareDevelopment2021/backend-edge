package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.ddo.DeviceModel;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public Response insertDevice(Device device) {
        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setName(device.getName());
        deviceModel.setModel(device.getModel());
        deviceDao.createDevice(deviceModel);
        return new Response(200L, "OK", "insert ok!");
    }

    @Override
    public Response deleteDevice(Long id) {
        deviceDao.removeDevice(id);
        return new Response(200L, "OK", "delete ok!");
    }

    @Override
    public Response updateDevice(Long id, Device device) {
        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setId(id);
        deviceModel.setName(device.getName());
        deviceModel.setModel(device.getModel());
        deviceDao.modifyDevice(deviceModel);
        return new Response(200L, "OK", "update ok!");
    }

    @Override
    public Response getDevice(Long id) {
        DeviceModel deviceModel = deviceDao.findDevice(id);
        Device device = new Device();
        device.setId(deviceModel.getId());
        device.setName(deviceModel.getName());
        device.setModel(deviceModel.getModel());
        return new Response(200L, "OK", device);
    }
}
