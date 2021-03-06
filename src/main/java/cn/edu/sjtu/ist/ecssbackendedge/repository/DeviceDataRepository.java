package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.device.DeviceDataPO;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备数据repository
 * @date 2021-11-08
 */
@Repository
public interface DeviceDataRepository extends MongoRepository<DeviceDataPO, String> {

    void deleteDeviceDataPOSByDeviceId(String deviceId);

    void deleteDeviceDataById(String id);

    DeviceDataPO findDeviceDataById(String id);

    DeviceDataPO findDeviceDataPOByDeviceIdAndSensorNameOrderByTimestamp(String deviceId, String sensorName);

    List<DeviceDataPO> findDeviceDataPOSByDeviceId(String deviceId);

    List<DeviceDataPO> findDeviceDataPOSByTimestampBeforeAndTimestampAfter(Date before, Date after);

}
