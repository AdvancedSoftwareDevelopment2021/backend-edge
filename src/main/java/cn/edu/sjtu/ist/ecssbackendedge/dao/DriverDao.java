package cn.edu.sjtu.ist.ecssbackendedge.dao;


import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.Command;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.driver.Driver;

import java.util.List;

/**
 * @author dyanjun
 * @date 2021/12/26 0:32
 */
public interface DriverDao {

    Driver createDriver(Driver driver);

    void removeDriver(String id);

    void modifyDriver(Driver driver);

    Driver findDriverById(String id);

    List<Driver> findDriverByDeviceId(String id);

    Driver findDriverByDeviceIDAndName(String deviceId, String name);

    List<Driver> findAllDriver();

    void saveStatus(String id, String deviceId, String status);
}
