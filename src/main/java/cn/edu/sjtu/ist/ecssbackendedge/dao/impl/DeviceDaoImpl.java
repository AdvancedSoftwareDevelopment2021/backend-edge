package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.ddo.DeviceModel;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.Device;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DeviceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DeviceDaoImpl implements DeviceDao {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public void createDevice(DeviceModel device) {
        Device device1 = new Device();
        device1.setName(device.getName());
        device1.setModel(device.getModel());
        deviceRepository.save(device1);
    }

    @Override
    public void removeDevice(Long id) {
        deviceRepository.deleteById(id);
    }

    @Override
    public void modifyDevice(DeviceModel device){
        Device device1 = deviceRepository.findDeviceById(device.getId());
        device1.setName(device.getName());
        device1.setModel(device.getModel());
        deviceRepository.save(device1);
    }

    @Override
    public DeviceModel findDevice(Long id) {
        Device device = deviceRepository.findDeviceById(id);
        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setId(device.getId());
        deviceModel.setName(device.getName());
        deviceModel.setModel(device.getModel());
        return deviceModel;
    }

    @Override
    public List<DeviceModel> findDevice(String name){
        List<Device> devices = deviceRepository.findDevicesByName(name);
        List<DeviceModel> deviceModels = new ArrayList<>();
        for (Device device: devices) {
            DeviceModel dm = new DeviceModel();
            dm.setId(device.getId());
            dm.setName(device.getName());
            dm.setModel(device.getModel());
            deviceModels.add(dm);
        }
        return deviceModels;
    }

}
