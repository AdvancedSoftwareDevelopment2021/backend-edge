package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.device.DeviceStatusPO;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备状态repository
 * @date 2021-11-08
 */
@Component
public interface DeviceStatusRepository extends MongoRepository<DeviceStatusPO, String> {

    void deleteDeviceStatusPOSByDeviceId(String deviceId);

    void deleteDeviceStatusById(String id);

    DeviceStatusPO findDeviceStatusById(String id);

    DeviceStatusPO findDeviceStatusPOByDeviceIdAndSensorNameOrderByTimestamp(String deviceId, String sensorName);

    List<DeviceStatusPO> findDeviceStatusPOSByDeviceId(String deviceId);

    List<DeviceStatusPO> findDeviceStatusPOSByTimestampBeforeAndTimestampAfter(Date before, Date after);

}
