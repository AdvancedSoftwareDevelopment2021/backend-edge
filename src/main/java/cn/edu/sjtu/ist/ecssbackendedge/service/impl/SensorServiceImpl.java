package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.SensorDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.Status;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.SensorStatus;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.SensorRequest;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.response.SensorResponse;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device.DataEntry;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.Sensor;
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
    public SensorResponse createSensor(String deviceId, SensorRequest sensorRequest) {
        Sensor sensor = sensorDao.findSensorByDeviceIDAndName(deviceId, sensorRequest.getName());
        if (sensor != null) {
            throw new RuntimeException("创建sensor名称" + sensorRequest.getName() + "失败，同名sensor已存在");
        }

        // 创建Sensor
        sensor = sensorUtil.convertRequestDTO2Domain(sensorRequest);
        sensor.setDeviceId(deviceId);
        sensor = sensorDao.createSensor(sensor);

        // 把sensorId装配进device
        Device device = deviceDao.findDeviceById(deviceId);
        for (DataEntry entry : device.getValues()) {
            if (entry.getName().equals(sensor.getName())) {
                entry.setSensorId(sensor.getId());
                break;
            }
        }
        deviceDao.modifyDevice(device);
        return sensorUtil.convertDomain2ResponseDTO(sensor);
    }

    @Override
    public void deleteSensorById(String id) {
        sensorDao.deleteSensorById(id);
    }

    @Override
    public void deleteSensorsByDeviceId(String id) {
        sensorDao.deleteSensorByDeviceId(id);
    }

    @Override
    public void syncStatus(Sensor sensor) {
        sensorDao.saveSensorStatus(sensor.getId(), sensor.getDeviceId(), sensor.getStatus().getType());
    }

    @Override
    public SensorResponse getSensorById(String id) {
        Sensor sensor = sensorDao.findSensorById(id);
        if (sensor != null) {
            return sensorUtil.convertDomain2ResponseDTO(sensor);
        } else {
            throw new RuntimeException("获取sensor id=" + id + "失败，sensor不存在");
        }
    }

    @Override
    public List<SensorResponse> getSensorsByDeviceId(String id) {
        List<Sensor> sensors = sensorDao.findSensorsByDeviceId(id);
        List<SensorResponse> res = new ArrayList<>();
        for (Sensor sensor : sensors) {
            res.add(sensorUtil.convertDomain2ResponseDTO(sensor));
        }
        return res;
    }

    @Override
    public String fetchLatestSensorStatus(String sensorId) {
        return sensorDao.fetchLatestSensorStatus(sensorId);
    }

    @Override
    public
    List<SensorStatus> getSensorHistoryAllStatus(String sensorId) {
        return sensorDao.fetchSensorAllStatus(sensorId);
    }

    @Override
    public void startSensor(String id, String sensorId) {
        Sensor sensor = sensorDao.findSensorById(sensorId);
        try {
            sensor.schedule(); // 开始采集数据
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stopSensor(String id, String sensorId) {
        Sensor sensor = sensorDao.findSensorById(sensorId);
        try {
            sensor.stopSchedule(); // 结束调度任务
        } catch (SchedulerException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startMonitor(String id, String sensorId) {
        Sensor sensor = sensorDao.findSensorById(sensorId);
        try {
            sensor.monitor(); // 开始监听
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stopMonitor(String id, String sensorId) {
        Sensor sensor = sensorDao.findSensorById(sensorId);
        try {
            sensor.stopMonitor(); // 停止监听
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
