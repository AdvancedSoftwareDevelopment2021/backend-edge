package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.Sensor;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.SensorStatus;

import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief SensorDao
 * @date 2021-11-19
 */
public interface SensorDao {

    Sensor createSensor(Sensor sensor);

    void deleteSensorById(String sensorId);

    void deleteSensorByDeviceId(String deviceId);

    void updateSensor(Sensor sensor);

    void saveSensorStatus(String sensorId, String deviceId, String status);

    Sensor findSensorById(String sensorId);

    Sensor findSensorByDeviceIDAndName(String deviceId, String name);

    List<Sensor> findSensorsByDeviceId(String deviceId);

    SensorStatus fetchLatestSensorStatus(String sensorId);

    List<SensorStatus> fetchSensorAllStatus(String sensorId);
}
