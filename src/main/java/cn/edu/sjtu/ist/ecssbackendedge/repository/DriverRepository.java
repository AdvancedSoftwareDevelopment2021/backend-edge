package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.driver.DriverPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dyanjun
 * @date 2021/12/26 0:33
 */
@Repository
public interface DriverRepository extends MongoRepository<DriverPO, String> {

    void deleteDriverPOById(String id);

    DriverPO findDriverById(String id);

    List<DriverPO> findDriverPOSByDeviceId(String id);

    List<DriverPO> findAll();

    DriverPO findDriverPOByDeviceIdAndName(String deviceId, String name);
}
