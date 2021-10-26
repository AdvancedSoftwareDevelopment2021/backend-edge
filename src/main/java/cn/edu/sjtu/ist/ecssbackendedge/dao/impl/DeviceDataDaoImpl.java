package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;

import cn.edu.sjtu.ist.ecssbackendedge.entity.ddo.DeviceProducedData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceData;
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
    public boolean createDeviceData(DeviceProducedData deviceProducedData) {
        DeviceData deviceData = new DeviceData();

        Device device = deviceRepository.findDeviceById(deviceProducedData.getDeviceId());
        if(device == null) {
            log.error("设备数据对应的设备不存在，数据无法保存");
            return false;
        }
        deviceData.setDevice(device);
        deviceData.setTimestamp(deviceProducedData.getTimestamp());
        deviceData.setData(deviceProducedData.getData());
        deviceDataRepository.save(deviceData);
        return true;
    }

    @Override
    public void removeDeviceDataById(Long id) {
        log.info("id: " + id);
        deviceDataRepository.deleteDeviceDataById(id);
    }

    @Override
    public boolean modifyDeviceData(DeviceProducedData deviceProducedData) {
        DeviceData deviceData = deviceDataRepository.findDeviceDataById(deviceProducedData.getId());
        if (deviceData == null) {
            log.info("device data id=" + deviceProducedData.getId() + " not exists!");
            return true;
        }
        Device device = deviceRepository.findDeviceById(deviceProducedData.getDeviceId());
        if (device == null) {
            log.info("device data id=" + deviceProducedData.getId() + ", device id=" + deviceProducedData.getDeviceId() +" not exists!");
            return false;
        }

        deviceData.setDevice(device);
        deviceData.setTimestamp(deviceProducedData.getTimestamp());
        deviceData.setData(deviceProducedData.getData());
        deviceDataRepository.save(deviceData);
        return true;
    }

    @Override
    public DeviceProducedData findDeviceDataById(Long id) {
        DeviceData deviceData = deviceDataRepository.findDeviceDataById(id);
        DeviceProducedData deviceProducedData = new DeviceProducedData();
        deviceProducedData.setId(deviceData.getId());
        deviceProducedData.setDeviceId(deviceData.getDevice().getId());
        deviceProducedData.setTimestamp(deviceData.getTimestamp());
        deviceProducedData.setData(deviceData.getData());
        return deviceProducedData;
    }
}
