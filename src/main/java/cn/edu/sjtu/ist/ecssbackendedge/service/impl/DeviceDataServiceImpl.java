package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.ddo.DeviceProducedData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    @Autowired
    private DeviceDataDao deviceDataDao;

    @Override
    public Response insertDeviceData(DeviceData deviceData) {
        DeviceProducedData deviceProducedData = new DeviceProducedData();
        deviceProducedData.setDeviceId(deviceData.getDeviceId());
        deviceProducedData.setTimestamp(deviceData.getTimestamp());
        deviceProducedData.setData(deviceData.getData());

        boolean ret = deviceDataDao.createDeviceData(deviceProducedData);
        if (ret) {
            return new Response(200, "OK", "insert device data ok!");
        } else {
            return new Response(400, "ERROR", "insert device data error! device id=" + deviceProducedData.getDeviceId() + " not exists!");
        }
    }

    @Override
    public Response deleteDeviceData(Long id) {
        deviceDataDao.removeDeviceDataById(id);
        return new Response(200, "OK", "delete device data ok!");
    }

    @Override
    public Response updateDeviceData(Long id, DeviceData deviceData) {
        DeviceProducedData deviceProducedData = new DeviceProducedData();
        deviceProducedData.setId(id);
        deviceProducedData.setDeviceId(deviceData.getDeviceId());
        deviceProducedData.setTimestamp(deviceData.getTimestamp());
        deviceProducedData.setData(deviceData.getData());
        if (deviceDataDao.modifyDeviceData(deviceProducedData)) {
            return new Response(200, "OK", "update device data ok!");
        } else {
            return new Response(400, "ERROR", "update device data error! device id=" + deviceProducedData.getDeviceId() + " not exists!");
        }
    }

    @Override
    public Response getDeviceData(Long id) {
        DeviceProducedData deviceProducedData = deviceDataDao.findDeviceDataById(id);
        DeviceData deviceData = new DeviceData();
        deviceData.setId(deviceProducedData.getId());
        deviceData.setDeviceId(deviceProducedData.getDeviceId());
        deviceData.setTimestamp(deviceProducedData.getTimestamp());
        deviceData.setData(deviceProducedData.getData());
        return new Response(200, "OK", deviceData);
    }
}
