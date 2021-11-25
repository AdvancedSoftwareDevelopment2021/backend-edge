package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDataDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
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
 * @brief 设备数据serviceImpl
 * @author rsp
 * @version 0.1
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
    public Response deleteDeviceHistoryData(String deviceId, String sensorName, String startTime, String endTime) {
        deviceDataDao.removeDeviceDataById(deviceId, startTime, endTime);
        return new Response(200, "OK", "删除设备数据成功!");
    }

    @Override
    public Response deleteDeviceAllHistoryData(String deviceId) {
        String startTime = "1000-01-01 08:00:00";
        String endTime = "3000-01-01 08:00:00";
        deviceDataDao.removeDeviceDataById(deviceId, startTime, endTime);
        return new Response(200, "OK", "删除设备数据成功!");
    }

    @Override
    public Response updateDeviceData(String id, DeviceDataDTO deviceDataDTO) {
        return new Response(200, "OK", "更新设备数据成功ok!");
    }


    @Override
    public Response getLatestDeviceData(String deviceId, String sensorName) {
        DeviceData deviceData = deviceDataDao.findLatestDeviceData(deviceId, sensorName);
        log.info("deviceData: " + deviceData);
        DeviceDataDTO deviceDataDTO = deviceDataUtil.convertDomain2DTO(deviceData);
        return new Response(200, "OK", deviceDataDTO);
    }

    @Override
    public Response getDeviceHistoryData(String deviceId, String sensorName, String filters, int pageIndex, int pageSize) {
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
            List<DeviceData> deviceDatas = deviceDataDao.findDeviceHistoryData(deviceId, sensorName, startTime, endTime, limit, offset);
            List<DeviceDataDTO> res = new ArrayList<>();
            for (DeviceData Data: deviceDatas) {
                res.add(deviceDataUtil.convertDomain2DTO(Data));
            }
            return new Response(200, "查询设备历史数据成功！", res);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new Response(200, "查询设备历史数据失败！", null);
        }
    }
}
