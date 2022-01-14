package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.SensorDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.Command;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device.DataEntry;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.Sensor;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.device.DeviceDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device.Device;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DeviceUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备serviceImpl
 * @date 2021-11-08
 */
@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceUtil deviceUtil;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private SensorDao sensorDao;

    @Override
    public DeviceDTO insertDevice(DeviceDTO deviceDTO) {
        Device device = deviceUtil.convertDTO2Domain(deviceDTO);
        Device device1 = deviceDao.createDevice(device);
        if (device1 == null) {
            throw new RuntimeException("插入设备失败!");
        } else {
            return deviceUtil.convertDomain2DTO(device1);
        }
    }

    @Override
    public void deleteDevice(String id) {
        deviceDao.removeDevice(id);
    }

    @Override
    public DeviceDTO updateDevice(String id, DeviceDTO deviceDTO) {

        deviceDTO.setId(id);
        Device device = deviceUtil.convertDTO2Domain(deviceDTO);
        deviceDao.modifyDevice(device);

        /**
         * 管理传感器
         */
        List<Sensor> sensors = sensorDao.findSensorsByDeviceId(device.getId());
        for(Sensor sensor: sensors){
            Boolean flag = false;
            for(DataEntry dataEntry: device.getValues()){
                if(sensor.getName().equals(dataEntry.getName())){
                    flag = true;
                }
            }
            if(!flag){
                sensorDao.deleteSensorById(sensor.getId());
            }
        }

        Device device1 = deviceDao.findDeviceByName(device.getName()).get(0);
        if (device1 == null) {
            throw new RuntimeException("更新设备信息失败!");
        } else {
            return deviceUtil.convertDomain2DTO(device1);
        }
    }

    @Override
    public DeviceDTO getDevice(String id) {
        Device device = deviceDao.findDeviceById(id);
        DeviceDTO deviceDTO = deviceUtil.convertDomain2DTO(device);
        return deviceDTO;
    }

    @Override
    public List<DeviceDTO> getAllDevices() {
        List<Device> devices = deviceDao.findAllDevices();
        List<DeviceDTO> res = new ArrayList<>();
        for (Device device : devices) {
            DeviceDTO dto = deviceUtil.convertDomain2DTO(device);
            res.add(dto);
        }
        return res;
    }
}
