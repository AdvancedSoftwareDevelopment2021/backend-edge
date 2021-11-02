package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.model.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DevicePO;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DeviceRepository;
import cn.edu.sjtu.ist.ecssbackendedge.utils.MessageProtocol;

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
        devicePO.setMessageProtocol(device.getMessageProtocol().getProtocol());
        deviceRepository.save(devicePO);
    }

    @Override
    public void removeDevice(Long id) {
        deviceRepository.deleteById(id);
    }

    @Override
    public void modifyDevice(Device device){
        DevicePO devicePO = deviceRepository.findDeviceById(device.getId());
        devicePO.setName(device.getName());
        devicePO.setModel(device.getModel());
        devicePO.setMessageProtocol(device.getMessageProtocol().getProtocol());
        deviceRepository.save(devicePO);
    }

    @Override
    public Device findDeviceById(Long id) {
        DevicePO devicePO = deviceRepository.findDeviceById(id);
        Device device = new Device();
        device.setId(devicePO.getId());
        device.setName(devicePO.getName());
        device.setModel(devicePO.getModel());
        device.setMessageProtocol(MessageProtocol.fromString(devicePO.getMessageProtocol()));
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
            dm.setMessageProtocol(MessageProtocol.fromString(devicePO.getMessageProtocol()));
            devices.add(dm);
        }
        return devices;
    }

}
