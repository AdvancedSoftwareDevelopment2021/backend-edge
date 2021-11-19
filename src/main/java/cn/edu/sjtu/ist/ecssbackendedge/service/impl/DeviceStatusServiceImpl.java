package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceStatusDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceStatusDTO;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceStatus;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceStatusService;

import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DeviceStatusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @brief 设备状态serviceImpl
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
@Slf4j
@Service
public class DeviceStatusServiceImpl implements DeviceStatusService {

    @Autowired
    private DeviceStatusUtil deviceStatusUtil;

    @Autowired
    private DeviceStatusDao deviceStatusDao;

    @Override
    public Response insertDeviceStatus(DeviceStatusDTO deviceStatusDTO) {
        DeviceStatus deviceStatus = deviceStatusUtil.convertDTO2Domain(deviceStatusDTO);
        boolean ret = deviceStatusDao.createDeviceStatus(deviceStatus);
        if (ret) {
            return new Response(200, "OK", "insert device status ok!");
        } else {
            return new Response(400, "ERROR", "insert device status error! device id=" + deviceStatus.getDeviceId() + " not exists!");
        }
    }

    @Override
    public Response deleteDeviceStatus(String id) {
        deviceStatusDao.removeDeviceStatusById(id);
        return new Response(200, "OK", "delete device status ok!");
    }

    @Override
    public Response updateDeviceStatus(String id, DeviceStatusDTO deviceStatusDTO) {
        deviceStatusDTO.setId(id);
        DeviceStatus deviceStatus = deviceStatusUtil.convertDTO2Domain(deviceStatusDTO);
        boolean ret = deviceStatusDao.modifyDeviceStatus(deviceStatus);
        if (ret) {
            return new Response(200, "OK", "update device status ok!");
        } else {
            return new Response(400, "ERROR", "update device status error! device id=" + deviceStatus.getDeviceId() + " not exists!");
        }
    }

    @Override
    public Response getDeviceStatus(String id) {
        DeviceStatus deviceStatus = deviceStatusDao.findDeviceStatusById(id);
        DeviceStatusDTO deviceStatusDTO = deviceStatusUtil.convertDomain2DTO(deviceStatus);
        return new Response(200, "OK", deviceStatusDTO);
    }
}
