package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.entity.ddo.DeviceOwnedStatus;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceStatus;
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
    public boolean createDeviceStatus(DeviceOwnedStatus deviceOwnedStatus) {
        Device device = deviceRepository.findDeviceById(deviceOwnedStatus.getDeviceId());
        if(device == null) {
            log.error("设备数据对应的设备不存在，数据无法保存");
            return false;
        }

        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setDevice(device);
        deviceStatus.setTimestamp(deviceOwnedStatus.getTimestamp());
        deviceStatus.setStatus(deviceOwnedStatus.getStatus());
        deviceStatusRepository.save(deviceStatus);
        return true;
    }

    @Override
    public void removeDeviceStatusById(Long id) {
        deviceStatusRepository.deleteDeviceStatusById(id);
    }

    @Override
    public boolean modifyDeviceStatus(DeviceOwnedStatus deviceOwnedStatus) {
        DeviceStatus deviceStatus = deviceStatusRepository.findDeviceStatusById(deviceOwnedStatus.getId());
        if (deviceStatus == null) {
            log.info("device data id=" + deviceOwnedStatus.getId() + " not exists!");
            return true;
        }
        Device device = deviceRepository.findDeviceById(deviceOwnedStatus.getDeviceId());
        if (device == null) {
            log.info("device data id=" + deviceOwnedStatus.getId() + ", device id=" + deviceOwnedStatus.getDeviceId() +" not exists!");
            return false;
        }

        deviceStatus.setDevice(device);
        deviceStatus.setTimestamp(deviceOwnedStatus.getTimestamp());
        deviceStatus.setStatus(deviceOwnedStatus.getStatus());
        deviceStatusRepository.save(deviceStatus);
        return true;
    }

    @Override
    public DeviceOwnedStatus findDeviceStatusById(Long id) {
        DeviceStatus deviceStatus = deviceStatusRepository.findDeviceStatusById(id);
        DeviceOwnedStatus deviceOwnedStatus = new DeviceOwnedStatus();
        deviceOwnedStatus.setId(deviceStatus.getId());
        deviceOwnedStatus.setDeviceId(deviceStatus.getDevice().getId());
        deviceOwnedStatus.setTimestamp(deviceStatus.getTimestamp());
        deviceOwnedStatus.setStatus(deviceStatus.getStatus());
        return deviceOwnedStatus;
    }

}
