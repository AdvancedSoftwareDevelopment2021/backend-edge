package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DevicePO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @brief 设备repository
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
@Repository
public interface DeviceRepository extends MongoRepository<DevicePO, String> {

    void deleteDevicePOById(String id);

    DevicePO findDeviceById(String id);

    List<DevicePO> findDevicePOSByName(String name);

    List<DevicePO> findAll();
}
