package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.SensorDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.collecting.SensorRequest;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.response.SensorResponse;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.DataEntry;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.Device;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.Sensor;
import cn.edu.sjtu.ist.ecssbackendedge.service.SensorService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.SensorUtil;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dyanjun
 * @date 2021/10/28 14:17
 */
@Slf4j
@Service
public class SensorServiceImpl implements SensorService {

    @Autowired
    private SensorDao sensorDao;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private SensorUtil sensorUtil;

    @Override
    public Response createSensor(String deviceId, SensorRequest sensorRequest) {
        Sensor sensor = sensorDao.findSensorByDeviceIDAndName(deviceId, sensorRequest.getName());
        if (sensor != null) {
            return new Response(300, "创建sensor名称"+sensorRequest.getName()+"失败，同名sensor已存在", null);
        }

        // 创建Sensor
        sensor = sensorUtil.convertRequestDTO2Domain(sensorRequest);
        sensor.setDeviceId(deviceId);
        sensorDao.createSensor(sensor);

        // 把sensorId装配进device
        sensor = sensorDao.findSensorByDeviceIDAndName(deviceId, sensorRequest.getName());
        Device device = deviceDao.findDeviceById(deviceId);
        for (DataEntry entry: device.getValues()) {
            if (entry.getName().equals(sensor.getName())) {
                entry.setSensorId(sensor.getId());
                break;
            }
        }
        deviceDao.modifyDevice(device);
        return new Response(200, "创建sensor名称"+sensorRequest.getName()+"成功", null);
    }

    @Override
    public Response deleteSensorById(String id) {
        sensorDao.deleteSensorById(id);
        return new Response(200, "删除sensor id=" + id + "成功", null);
    }

    @Override
    public Response deleteSensorsByDeviceId(String id) {
        sensorDao.deleteSensorByDeviceId(id);
        return new Response(200, "删除设备(id=" + id + ")的sensors成功", null);
    }

    @Override
    public Response syncStatus(Sensor sensor) {
        sensorDao.updateSensorStatus(sensor.getId(), sensor.getStatus());
        return new Response(200, "更新sensor id=" + sensor.getId() + "成功", null);
    }

    @Override
    public Response getSensorById(String id) {
        Sensor sensor = sensorDao.findSensorById(id);
        if (sensor != null) {
            return new Response(200, "获取sensor id=" + id + "成功", sensorUtil.convertDomain2ResponseDTO(sensor));
        } else {
            return new Response(200, "获取sensor id=" + id + "失败，sensor不存在", null);
        }
    }

    @Override
    public Response getSensorsByDeviceId(String id) {
        List<Sensor> sensors = sensorDao.findSensorsByDeviceId(id);
        List<SensorResponse> res = new ArrayList<>();
        for (Sensor sensor : sensors) {
            res.add(sensorUtil.convertDomain2ResponseDTO(sensor));
        }
        return new Response(200, "获取设备(id=" + id + ")的sensors成功", res);
    }

    @Override
    public Response startSensor(String id, String sensorId) {
        Sensor sensor = sensorDao.findSensorById(sensorId);
        try {
            sensor.schedule(); // 开始采集数据
            return new Response(200, "sensor" + sensor.getName() + "启动成功！", null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response stopSensor(String id, String sensorId) {
        Sensor sensor = sensorDao.findSensorById(sensorId);
        try {
            sensor.stopSchedule(); // 结束调度任务
            return new Response(200, "sensor" + sensor.getName() + "关闭成功！", null);
        } catch (SchedulerException e) {
            log.info(e.getMessage());
            return new Response(200, "sensor" + sensor.getName() + "关闭失败！", null);
        }
    }
}
