package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.model.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DevicePO;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DeviceRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DeviceDaoImpl implements DeviceDao {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public void createDevice(Device device) {
        DevicePO devicePO = new DevicePO();
        devicePO.setName(device.getName());
        devicePO.setModel(device.getModel());
        deviceRepository.save(devicePO);
    }

    @Override
    public void removeDevice(String id) {
        deviceRepository.deleteById(id);
    }

    @Override
    public void modifyDevice(Device device){
        DevicePO devicePO = deviceRepository.findDeviceById(device.getId());
        devicePO.setName(device.getName());
        devicePO.setModel(device.getModel());
        deviceRepository.modifyDevice(devicePO);
    }

    @Override
    public List<Device> findDeviceAll(){
        List<DevicePO> devicePOList = deviceRepository.findDeviceAll();
        List<Device> deviceList = new ArrayList<>();
        for(DevicePO devicePO : devicePOList){
            Device device = new Device();
            device.setId(devicePO.getId());
            device.setName(devicePO.getName());
            device.setModel(devicePO.getModel());
            deviceList.add(device);
        }
        return deviceList;
    }

    @Override
    public Device findDeviceById(String id) {
        DevicePO devicePO = deviceRepository.findDeviceById(id);
        Device device = new Device();
        device.setId(devicePO.getId());
        device.setName(devicePO.getName());
        device.setModel(devicePO.getModel());
        log.info(String.valueOf(device));
        return device;
    }

    @Override
    public List<Device> findDeviceByName(String name){
        List<DevicePO> devicePOs = deviceRepository.findDevicesByName(name);
        List<Device> devices = new ArrayList<>();
        for (DevicePO devicePO: devicePOs) {
            Device dm = new Device();
            dm.setId(devicePO.getId());
            dm.setName(devicePO.getName());
            dm.setModel(devicePO.getModel());
            devices.add(dm);
        }
        return devices;
    }

}
