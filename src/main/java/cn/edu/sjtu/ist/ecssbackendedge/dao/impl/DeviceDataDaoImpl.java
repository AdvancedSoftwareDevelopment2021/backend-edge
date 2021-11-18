package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DevicePO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceDataPO;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DeviceDataRepository;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DeviceRepository;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DeviceDataUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @brief 设备数据DaoImpl
 * @author rsp
 * @version 0.1
 * @date 2021-11-19
 */
@Slf4j
@Component
public class DeviceDataDaoImpl implements DeviceDataDao {

    @Autowired
    private DeviceDataUtil deviceDataUtil;

    @Autowired
    private DeviceDataRepository deviceDataRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public boolean createDeviceData(DeviceData deviceData) {
        DevicePO devicePO = deviceRepository.findDeviceById(deviceData.getDeviceId());
        if(devicePO == null) {
            log.error("设备数据对应的设备不存在，数据无法保存");
            return false;
        }

        DeviceDataPO deviceDataPO = deviceDataUtil.convertDomain2PO(deviceData);
        deviceDataRepository.save(deviceDataPO);
        return true;
    }

    @Override
    public void saveDeviceData(String deviceId, String data) {
        DeviceDataPO po = new DeviceDataPO();
        po.setDeviceId(deviceId);
        po.setTimestamp(new Date());
        po.setData(data);
        deviceDataRepository.save(po);
    }

    @Override
    public void removeDeviceDataById(String id) {
        log.info("id: " + id);
        deviceDataRepository.deleteDeviceDataById(id);
    }

    @Override
    public boolean modifyDeviceData(DeviceData deviceData) {
        DevicePO devicePO = deviceRepository.findDeviceById(deviceData.getDeviceId());
        if (devicePO == null) {
            log.info("设备数据id=" + deviceData.getId() + ", 设备id=" + deviceData.getDeviceId() +"不存在!");
            return false;
        }

        DeviceDataPO deviceDataPO = deviceDataRepository.findDeviceDataById(deviceData.getId());
        if (deviceDataPO != null) {
            deviceDataPO = deviceDataUtil.convertDomain2PO(deviceData);
            deviceDataRepository.save(deviceDataPO);
        }
        log.info("设备数据id=" + deviceData.getId() + "不存在!");
        return true;
    }

    @Override
    public DeviceData findDeviceDataById(String id) {
        DeviceDataPO deviceDataPO = deviceDataRepository.findDeviceDataById(id);
        return deviceDataUtil.convertPO2Domain(deviceDataPO);
    }
}
