package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceStatusDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.device.DevicePO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.device.DeviceStatusPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device.DeviceStatus;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DeviceRepository;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DeviceStatusRepository;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DeviceStatusUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备状态DaoImpl
 * @date 2021-11-19
 */
@Slf4j
@Profile("mongodb")
@Component
public class DeviceStatusDaoImpl implements DeviceStatusDao {

    @Autowired
    private DeviceStatusUtil deviceStatusUtil;

    @Autowired
    private DeviceStatusRepository deviceStatusRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public void saveDeviceStatus(String deviceId, String sensorName, String status) {
        DeviceStatusPO po = new DeviceStatusPO();
        po.setDeviceId(deviceId);
        po.setTimestamp(new Date());
        po.setStatus(status);
        deviceStatusRepository.save(po);
    }

    @Override
    public boolean createDeviceStatus(DeviceStatus deviceStatus) {
        DevicePO devicePO = deviceRepository.findDeviceById(deviceStatus.getDeviceId());
        if (devicePO == null) {
            log.error("设备数据对应的设备不存在，数据无法保存");
            return false;
        }

        DeviceStatusPO deviceStatusPO = deviceStatusUtil.convertDomain2PO(deviceStatus);
        deviceStatusRepository.save(deviceStatusPO);
        return true;
    }

    @Override
    public void removeDeviceStatusById(String deviceId, String startTime, String endTime) {
//        deviceStatusRepository.deleteDeviceStatusById(id);
    }

    @Override
    public boolean modifyDeviceStatus(DeviceStatus deviceStatus) {
        DevicePO devicePO = deviceRepository.findDeviceById(deviceStatus.getDeviceId());
        if (devicePO == null) {
            log.info("设备状态id=" + deviceStatus.getId() + ", 设备id=" + deviceStatus.getDeviceId() + "不存在!");
            return false;
        }

        DeviceStatusPO deviceStatusPO = deviceStatusRepository.findDeviceStatusById(deviceStatus.getId());
        if (deviceStatusPO == null) {
            deviceStatusPO = deviceStatusUtil.convertDomain2PO(deviceStatus);
            deviceStatusRepository.save(deviceStatusPO);
        }
        log.info("设备状态id=" + deviceStatus.getId() + " 不存在!");
        return true;
    }

    @Override
    public DeviceStatus findLatestDeviceStatus(String deviceId, String sensorName) {
        DeviceStatusPO deviceStatusPO = deviceStatusRepository.findDeviceStatusPOByDeviceIdAndSensorNameOrderByTimestamp(deviceId, sensorName);
        return deviceStatusUtil.convertPO2Domain(deviceStatusPO);
    }

    @Override
    public List<DeviceStatus> findDeviceHistoryStatus(String deviceId, String sensorName, String startTime, String endTime, int limit, int offset) {
        return new ArrayList<>();
    }
}
