package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.SensorRequest;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.response.SensorResponse;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.Sensor;

import java.util.List;

/**
 * @author dyanjun
 * @date 2021/10/28 14:18
 */
public interface SensorService {

    SensorResponse createSensor(String deviceId, SensorRequest sensorDTO);

    void deleteSensorById(String id);

    void deleteSensorsByDeviceId(String id);

    void syncStatus(Sensor sensor);

    SensorResponse getSensorById(String id);

    List<SensorResponse> getSensorsByDeviceId(String id);

    void startSensor(String id, String sensorId);

    void stopSensor(String id, String sensorId);

    void startMonitor(String id, String sensorId);

    void stopMonitor(String id, String sensorId);
}
