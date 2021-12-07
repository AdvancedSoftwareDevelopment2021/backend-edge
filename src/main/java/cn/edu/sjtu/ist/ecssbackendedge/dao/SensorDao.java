package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.Status;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.Sensor;

import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief SensorDao
 * @date 2021-11-19
 */
public interface SensorDao {

    Sensor createSensor(Sensor sensor);

    void deleteSensorById(String id);

    void deleteSensorByDeviceId(String deviceId);

    void updateSensor(Sensor sensor);

    void updateSensorStatus(String id, Status status);

    Sensor findSensorById(String id);

    Sensor findSensorByDeviceIDAndName(String deviceId, String name);

    List<Sensor> findSensorsByDeviceId(String id);

}
