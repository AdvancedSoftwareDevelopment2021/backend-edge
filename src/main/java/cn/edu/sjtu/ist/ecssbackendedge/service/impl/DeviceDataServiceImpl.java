package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDataDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceDataService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DeviceDataUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @brief 设备数据serviceImpl
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    @Autowired
    private DeviceDataUtil deviceDataUtil;

    @Autowired
    private DeviceDataDao deviceDataDao;

    @Override
    public Response insertDeviceData(DeviceDataDTO deviceDataDTO) {
        DeviceData deviceData = deviceDataUtil.convertDTO2Domain(deviceDataDTO);
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
        deviceDataDTO.setId(id);
        DeviceData deviceData = deviceDataUtil.convertDTO2Domain(deviceDataDTO);
        if (deviceDataDao.modifyDeviceData(deviceData)) {
            return new Response(200, "OK", "update device data ok!");
        } else {
            return new Response(400, "ERROR", "update device data error! device id=" + deviceData.getDeviceId() + " not exists!");
        }
    }

    @Override
    public Response getDeviceData(String id) {
        DeviceData deviceData = deviceDataDao.findDeviceDataById(id);
        DeviceDataDTO deviceDataDTO = deviceDataUtil.convertDomain2DTO(deviceData);
        return new Response(200, "OK", deviceDataDTO);
    }
}
