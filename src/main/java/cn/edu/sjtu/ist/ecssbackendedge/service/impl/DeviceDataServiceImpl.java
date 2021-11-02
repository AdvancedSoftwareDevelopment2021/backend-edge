package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.model.DeviceData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDataDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    @Autowired
    private DeviceDataDao deviceDataDao;

    @Override
    public Response insertDeviceData(DeviceDataDTO deviceDataDTO) {
        DeviceData deviceData = new DeviceData();
        deviceData.setDeviceId(deviceDataDTO.getDeviceId());
        deviceData.setTimestamp(deviceDataDTO.getTimestamp());
        deviceData.setData(deviceDataDTO.getData());

        boolean ret = deviceDataDao.createDeviceData(deviceData);
        if (ret) {
            return new Response(200, "OK", "insert device data ok!");
        } else {
            return new Response(400, "ERROR", "insert device data error! device id=" + deviceData.getDeviceId() + " not exists!");
        }
    }

    @Override
    public Response deleteDeviceData(String id) {
        deviceDataDao.removeDeviceDataById(id);
        return new Response(200, "OK", "delete device data ok!");
    }

    @Override
    public Response updateDeviceData(String id, DeviceDataDTO deviceDataDTO) {
        DeviceData deviceData = new DeviceData();
        deviceData.setId(id);
        deviceData.setDeviceId(deviceDataDTO.getDeviceId());
        deviceData.setTimestamp(deviceDataDTO.getTimestamp());
        deviceData.setData(deviceDataDTO.getData());
        if (deviceDataDao.modifyDeviceData(deviceData)) {
            return new Response(200, "OK", "update device data ok!");
        } else {
            return new Response(400, "ERROR", "update device data error! device id=" + deviceData.getDeviceId() + " not exists!");
        }
    }

    @Override
    public Response getDeviceData(String id) {
        DeviceData deviceData = deviceDataDao.findDeviceDataById(id);
        DeviceDataDTO deviceDataDTO = new DeviceDataDTO();
        deviceDataDTO.setId(deviceData.getId());
        deviceDataDTO.setDeviceId(deviceData.getDeviceId());
        deviceDataDTO.setTimestamp(deviceData.getTimestamp());
        deviceDataDTO.setData(deviceData.getData());
        return new Response(200, "OK", deviceDataDTO);
    }
}
