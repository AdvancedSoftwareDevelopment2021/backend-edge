package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.Sensor;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.Status;

import java.util.List;

/**
 * @brief SensorDao
 * @author rsp
 * @version 0.1
 * @date 2021-11-19
 */
public interface SensorDao {

    void createSensor(Sensor sensor);

    void deleteSensorById(String id);

    void deleteSensorByDeviceId(String deviceId);

    Sensor findSensorById(String id);

    List<Sensor> findSensorsByDeviceId(String id);

    void updateSensor(Sensor sensor);

    void updateSensorStatus(String id, Status status);
}