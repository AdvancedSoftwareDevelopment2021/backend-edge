package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceStatusDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.device.DeviceStatusDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device.DeviceStatus;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceStatusService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DeviceStatusUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备状态serviceImpl
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
    public void insertDeviceStatus(DeviceStatusDTO deviceStatusDTO) {
        DeviceStatus deviceStatus = deviceStatusUtil.convertDTO2Domain(deviceStatusDTO);
        boolean ret = deviceStatusDao.createDeviceStatus(deviceStatus);
        if (!ret) {
            throw new RuntimeException("insert device status error! device id=" + deviceStatus.getDeviceId() + " not exists!");
        }
    }

    @Override
    public void deleteDeviceHistoryStatus(String deviceId, String sensorName, String startTime, String endTime) {
        deviceStatusDao.removeDeviceStatusById(deviceId, startTime, endTime);
    }

    @Override
    public void deleteDeviceAllHistoryStatus(String deviceId) {
        String startTime = "1000-01-01 08:00:00";
        String endTime = "3000-01-01 08:00:00";
        deviceStatusDao.removeDeviceStatusById(deviceId, startTime, endTime);
    }

    @Override
    public void updateDeviceStatus(String id, DeviceStatusDTO deviceStatusDTO) {
        deviceStatusDTO.setId(id);
        DeviceStatus deviceStatus = deviceStatusUtil.convertDTO2Domain(deviceStatusDTO);
        boolean ret = deviceStatusDao.modifyDeviceStatus(deviceStatus);
        if (!ret) {
            throw new RuntimeException("update device status error! device id=" + deviceStatus.getDeviceId() + " not exists!");
        }
    }

    @Override
    public DeviceStatusDTO getLatestDeviceStatus(String deviceId, String sensorName) {
        DeviceStatus deviceStatus = deviceStatusDao.findLatestDeviceStatus(deviceId, sensorName);
        log.info("deviceStatus: " + deviceStatus);
        return deviceStatusUtil.convertDomain2DTO(deviceStatus);
    }

    @Override
    public List<DeviceStatusDTO> getDeviceHistoryStatus(String deviceId, String sensorName, String filters, int pageIndex, int pageSize) {
        try {
            String filterString = URLDecoder.decode(filters, "UTF-8");
            JSONObject filterObj = (JSONObject) JSON.parse(filterString);
            System.out.println(filterObj);

            String startTime, endTime;
            if (filterObj.containsKey("startTime") && filterObj.containsKey("endTime")) {
                startTime = filterObj.getString("startTime");
                endTime = filterObj.getString("endTime");
            } else {
                startTime = "1021-03-29 14:33:01";
                endTime = "3021-03-29 14:33:01";
            }

            int offset = (pageIndex - 1) * pageSize;
            int limit = pageSize;
            List<DeviceStatus> deviceStatuses = deviceStatusDao.findDeviceHistoryStatus(deviceId, sensorName, startTime, endTime, limit, offset);
            List<DeviceStatusDTO> res = new ArrayList<>();
            for (DeviceStatus status : deviceStatuses) {
                res.add(deviceStatusUtil.convertDomain2DTO(status));
            }
            return res;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("查询设备历史状态失败！");
        }

    }
}
