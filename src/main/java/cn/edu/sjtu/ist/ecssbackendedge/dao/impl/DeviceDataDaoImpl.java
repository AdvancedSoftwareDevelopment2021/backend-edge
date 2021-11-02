package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;

import cn.edu.sjtu.ist.ecssbackendedge.model.DeviceData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DevicePO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceDataPO;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DeviceDataRepository;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DeviceRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeviceDataDaoImpl implements DeviceDataDao {

    @Autowired
    private DeviceDataRepository deviceDataRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public boolean createDeviceData(DeviceData deviceData) {
        DeviceDataPO deviceDataPO = new DeviceDataPO();

        log.info(deviceData.getDeviceId());
        DevicePO devicePO = deviceRepository.findDeviceById(deviceData.getDeviceId());
        if(devicePO == null) {
            log.error("设备数据对应的设备不存在，数据无法保存");
            return false;
        }
        deviceDataPO.setDevice(devicePO);
        deviceDataPO.setTimestamp(deviceData.getTimestamp());
        deviceDataPO.setData(deviceData.getData());
        deviceDataRepository.save(deviceDataPO);
        return true;
    }

    @Override
    public void removeDeviceDataById(String id) {
        log.info("id: " + id);
        deviceDataRepository.deleteDeviceDataById(id);
    }

    @Override
    public boolean modifyDeviceData(DeviceData deviceData) {
        DeviceDataPO deviceDataPO = deviceDataRepository.findDeviceDataById(deviceData.getId());
        if (deviceDataPO == null) {
            log.info("device data id=" + deviceData.getId() + " not exists!");
            return true;
        }
        DevicePO devicePO = deviceRepository.findDeviceById(deviceData.getDeviceId());
        if (devicePO == null) {
            log.info("device data id=" + deviceData.getId() + ", device id=" + deviceData.getDeviceId() +" not exists!");
            return false;
        }

        deviceDataPO.setDevice(devicePO);
        deviceDataPO.setTimestamp(deviceData.getTimestamp());
        deviceDataPO.setData(deviceData.getData());
        deviceDataRepository.modifyDeviceData(deviceDataPO);
        return true;
    }

    @Override
    public DeviceData findDeviceDataById(String id) {
        DeviceDataPO deviceDataPO = deviceDataRepository.findDeviceDataById(id);
        DeviceData deviceData = new DeviceData();
        deviceData.setId(deviceDataPO.getId());
        deviceData.setDeviceId(deviceDataPO.getDevice().getId());
        deviceData.setTimestamp(deviceDataPO.getTimestamp());
        deviceData.setData(deviceDataPO.getData());
        return deviceData;
    }
}
