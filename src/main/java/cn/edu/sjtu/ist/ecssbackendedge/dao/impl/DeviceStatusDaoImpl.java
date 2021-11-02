package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.model.DeviceStatus;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DevicePO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceStatusPO;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceStatusDao;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DeviceRepository;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DeviceStatusRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Component
public class DeviceStatusDaoImpl implements DeviceStatusDao {

    @Autowired
    private DeviceStatusRepository deviceStatusRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public boolean createDeviceStatus(DeviceStatus deviceStatus) {
        DevicePO devicePO = deviceRepository.findDeviceById(deviceStatus.getDeviceId());
        if(devicePO == null) {
            log.error("设备数据对应的设备不存在，数据无法保存");
            return false;
        }

        DeviceStatusPO deviceStatusPO = new DeviceStatusPO();
        deviceStatusPO.setDevice(devicePO);
        deviceStatusPO.setTimestamp(deviceStatus.getTimestamp());
        deviceStatusPO.setStatus(deviceStatus.getStatus());
        deviceStatusRepository.save(deviceStatusPO);
        return true;
    }

    @Override
    public void removeDeviceStatusById(Long id) {
        deviceStatusRepository.deleteDeviceStatusById(id);
    }

    @Override
    public boolean modifyDeviceStatus(DeviceStatus deviceStatus) {
        DeviceStatusPO deviceStatusPO = deviceStatusRepository.findDeviceStatusById(deviceStatus.getId());
        if (deviceStatusPO == null) {
            log.info("device data id=" + deviceStatus.getId() + " not exists!");
            return true;
        }
        DevicePO devicePO = deviceRepository.findDeviceById(deviceStatus.getDeviceId());
        if (devicePO == null) {
            log.info("device data id=" + deviceStatus.getId() + ", device id=" + deviceStatus.getDeviceId() +" not exists!");
            return false;
        }

        deviceStatusPO.setDevice(devicePO);
        deviceStatusPO.setTimestamp(deviceStatus.getTimestamp());
        deviceStatusPO.setStatus(deviceStatus.getStatus());
        deviceStatusRepository.save(deviceStatusPO);
        return true;
    }

    @Override
    public DeviceStatus findDeviceStatusById(Long id) {
        DeviceStatusPO deviceStatusPO = deviceStatusRepository.findDeviceStatusById(id);
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setId(deviceStatusPO.getId());
        deviceStatus.setDeviceId(deviceStatusPO.getDevice().getId());
        deviceStatus.setTimestamp(deviceStatusPO.getTimestamp());
        deviceStatus.setStatus(deviceStatusPO.getStatus());
        return deviceStatus;
    }

}
