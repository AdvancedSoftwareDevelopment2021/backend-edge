package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.SensorRequest;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.Sensor;

/**
 * @author dyanjun
 * @date 2021/10/28 14:18
 */
public interface SensorService {

    Response createSensor(String deviceId, SensorRequest sensorDTO);

    Response deleteSensorById(String id);

    Response deleteSensorsByDeviceId(String id);

    Response syncStatus(Sensor sensor);

    Response getSensorById(String id);

    Response getSensorsByDeviceId(String id);

    Response startSensor(String id, String sensorId);

    Response stopSensor(String id, String sensorId);
}
