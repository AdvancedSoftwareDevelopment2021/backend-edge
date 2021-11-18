package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.model.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceService;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;


import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public Response insertDevice(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setName(deviceDTO.getName());
        device.setModel(deviceDTO.getModel());
        deviceDao.createDevice(device);
        return new Response(200, "OK", "insert ok!");
    }

    @Override
    public Response deleteDevice(String id) {
        deviceDao.removeDevice(id);
        return new Response(200, "OK", "delete ok!");
    }

    @Override
    public Response updateDevice(String id, DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setId(id);
        device.setName(deviceDTO.getName());
        device.setModel(deviceDTO.getModel());
        deviceDao.modifyDevice(device);
        return new Response(200, "OK", "update ok!");
    }

    @Override
    public Response getDevice(String id) {
        Device device = deviceDao.findDeviceById(id);
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setId(device.getId());
        deviceDTO.setName(device.getName());
        deviceDTO.setModel(device.getModel());
        return new Response(200, "OK", device);
    }

    @Override
    public Response getDeviceAll(){
        List<Device> deviceList = deviceDao.findDeviceAll();
        List<DeviceDTO> deviceDTOList = new ArrayList<>();
        for(Device device : deviceList){
            DeviceDTO deviceDTO = new DeviceDTO();
            deviceDTO.setId(device.getId());
            deviceDTO.setName(device.getName());
            deviceDTO.setModel(device.getModel());
            deviceDTOList.add(deviceDTO);
        }
        return  new Response(200, "OK", deviceDTOList);
    }
}
