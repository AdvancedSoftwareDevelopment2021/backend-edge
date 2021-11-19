package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.sensor.SensorPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dyanjun
 * @date 2021/10/28 14:32
 */
@Repository
public interface SensorRepository extends MongoRepository<SensorPO, String> {

    void deleteSensorPOById(String id);

    void deleteSensorPOSByDeviceId(String deviceId);

    SensorPO findSensorPOById(String id);

    List<SensorPO> findSensorPOSByDeviceId(String deviceId);

    SensorPO findSensorPOByDeviceIdAndName(String deviceId, String name);
}