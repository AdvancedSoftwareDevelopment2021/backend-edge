package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDataDTO;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceData;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceDataService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DeviceDataUtil;
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
 * @brief 设备数据serviceImpl
 * @date 2021-11-08
 */
@Slf4j
@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    @Autowired
    private DeviceDataUtil deviceDataUtil;

    @Autowired
    private DeviceDataDao deviceDataDao;

    @Override
    public void insertDeviceData(DeviceDataDTO deviceDataDTO) {
        DeviceData deviceData = deviceDataUtil.convertDTO2Domain(deviceDataDTO);
        boolean ret = deviceDataDao.createDeviceData(deviceData);
        if (!ret) {
            throw new RuntimeException("insert device data error! device id=" + deviceData.getDeviceId() + " not exists!");
        }
    }

    @Override
    public void deleteDeviceHistoryData(String deviceId, String sensorName, String startTime, String endTime) {
        deviceDataDao.removeDeviceDataById(deviceId, startTime, endTime);
    }

    @Override
    public void deleteDeviceAllHistoryData(String deviceId) {
        String startTime = "1000-01-01 08:00:00";
        String endTime = "3000-01-01 08:00:00";
        deviceDataDao.removeDeviceDataById(deviceId, startTime, endTime);
    }

    @Override
    public void updateDeviceData(String id, DeviceDataDTO deviceDataDTO) {
    }


    @Override
    public DeviceDataDTO getLatestDeviceData(String deviceId, String sensorName) {
        DeviceData deviceData = deviceDataDao.findLatestDeviceData(deviceId, sensorName);
        log.info("deviceData: " + deviceData);
        return deviceDataUtil.convertDomain2DTO(deviceData);
    }

    @Override
    public List<DeviceDataDTO> getDeviceHistoryData(String deviceId, String sensorName, String filters, int pageIndex, int pageSize) {
        try {
            String filterString = URLDecoder.decode(filters, "UTF-8");
            JSONObject filterObj = (JSONObject) JSON.parse(filterString);

            String startTime, endTime;
            if (filterObj != null && filterObj.containsKey("startTime") && filterObj.containsKey("endTime")) {
                startTime = filterObj.getString("startTime");
                endTime = filterObj.getString("endTime");
            } else {
                startTime = "1021-03-29 14:33:01";
                endTime = "3021-03-29 14:33:01";
            }

            int offset = (pageIndex - 1) * pageSize;
            int limit = pageSize;
            List<DeviceData> deviceDatas = deviceDataDao.findDeviceHistoryDataWithLimit(deviceId, sensorName, startTime, endTime, limit, offset);
            List<DeviceDataDTO> res = new ArrayList<>();
            for (DeviceData Data : deviceDatas) {
                res.add(deviceDataUtil.convertDomain2DTO(Data));
            }
            return res;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("查询设备历史数据失败！");
        }
    }

    @Override
    public List<DeviceDataDTO> getDeviceAllHistoryData(String deviceId, String sensorName) {
        List<DeviceData> deviceDatas = deviceDataDao.findDeviceAllHistoryData(deviceId, sensorName);
        List<DeviceDataDTO> res = new ArrayList<>();
        for (DeviceData Data : deviceDatas) {
            res.add(deviceDataUtil.convertDomain2DTO(Data));
        }
        return res;
    }
}
