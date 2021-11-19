package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.model.device.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DevicePO;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DeviceRepository;

import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DeviceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * @brief 设备DaoImpl
 * @author rsp
 * @version 0.1
 * @date 2021-11-19
 */
@Slf4j
@Component
public class DeviceDaoImpl implements DeviceDao {

    @Autowired
    private DeviceUtil deviceUtil;

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public void createDevice(Device device) {
        DevicePO devicePO = deviceUtil.convertDomain2PO(device);
        deviceRepository.save(devicePO);
    }

    @Override
    public void removeDevice(String id) {
        deviceRepository.deleteDevicePOById(id);
    }

    @Override
    public void modifyDevice(Device device) {
        DevicePO devicePO = deviceRepository.findDeviceById(device.getId());
        if (devicePO != null) {
            devicePO = deviceUtil.convertDomain2PO(device);
            deviceRepository.save(devicePO);
        }
    }

    @Override
    public List<Device> findDeviceAll(){
        List<DevicePO> devicePOList = deviceRepository.findAll();
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
        Device device = deviceUtil.convertPO2Domain(devicePO);
        log.info(String.valueOf(device));
        return device;
    }

    @Override
    public List<Device> findDeviceByName(String name){
        List<DevicePO> devicePOs = deviceRepository.findDevicePOSByName(name);
        List<Device> devices = new ArrayList<>();
        for (DevicePO devicePO: devicePOs) {
            Device dm = deviceUtil.convertPO2Domain(devicePO);
            devices.add(dm);
        }
        return devices;
    }

    @Override
    public List<Device> findAllDevices() {
        List<DevicePO> devicePOs = deviceRepository.findAll();
        List<Device> devices = new ArrayList<>();
        for (DevicePO devicePO: devicePOs) {
            Device dm = deviceUtil.convertPO2Domain(devicePO);
            devices.add(dm);
        }
        return devices;
    }
}
