package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DriverDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.driver.Driver;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.driver.DriverPO;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DriverRepository;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DriverUtil;
import cn.edu.sjtu.ist.ecssbackendedge.utils.storage.IotdbDriverStatusUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.Session;
import org.apache.iotdb.session.pool.SessionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author dyanjun
 * @date 2021/12/26 0:32
 */
@Slf4j
@Component
public class DriverDaoImpl implements DriverDao {

    @Autowired
    private DriverUtil driverUtil;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private Session session;

    @Autowired
    private SessionPool sessionPool;

    @Override
    public Driver createDriver(Driver driver) {
        DriverPO po = driverUtil.convertDomain2PO(driver);
        driverRepository.save(po);
        return driverUtil.convertPO2Domain(po);
    }

    @Override
    public void removeDriver(String id) {
        driverRepository.deleteDriverPOById(id);
    }

    @Override
    public void modifyDriver(Driver driver) {
        DriverPO po = driverRepository.findDriverById(driver.getId());
        if (po != null) {
            po = driverUtil.convertDomain2PO(driver);
            driverRepository.save(po);
        } else throw new RuntimeException("控制器不存在");
    }

    @Override
    public Driver findDriverById(String id) {
        DriverPO po = driverRepository.findDriverById(id);
        if(po != null){
            return driverUtil.convertPO2Domain(po);
        } else throw new RuntimeException("控制器不存在");
    }

    @Override
    public List<Driver> findDriverByDeviceId(String id) {
       List<DriverPO> driverPOS = driverRepository.findDriverPOSByDeviceId(id);
       List<Driver> drivers = new ArrayList<>();
       for(DriverPO driverPO: driverPOS){
           drivers.add(driverUtil.convertPO2Domain(driverPO));
       }
       return drivers;
    }

    @Override
    public Driver findDriverByDeviceIDAndName(String deviceId, String name) {
        DriverPO po = driverRepository.findDriverPOByDeviceIdAndName(deviceId, name);
        if (po != null) {
            Driver driver = driverUtil.convertPO2Domain(po);
            return driver;
        }
        return null;
    }

    @Override
    public List<Driver> findAllDriver() {
        List<DriverPO> driverPOS = driverRepository.findAll();
        List<Driver> drivers = new ArrayList<>();
        for(DriverPO driverPO: driverPOS){
            drivers.add(driverUtil.convertPO2Domain(driverPO));
        }
        return drivers;
    }

    @Override
    public void saveStatus(String id, String deviceId, String status) {
        String tableName = IotdbDriverStatusUtil.getDriverStatusTimeSeries(id);
        List<String> measurements = Arrays.asList("deviceId", "status");
        List<String> values = Arrays.asList(deviceId, status);

        try {
            sessionPool.insertRecord(tableName, (new Date()).getTime(), measurements, values);
            log.info(String.format("iotdb: 插入状态成功！ 表名=%s", tableName));
        } catch (IoTDBConnectionException | StatementExecutionException e) {
            log.error(String.format("iotdb: 插入控制器状态失败，控制器id=%s，状态=%s", id, status));
            throw new RuntimeException("iotdb: 插入控制器状态失败，报错信息：" + e.getMessage());
        }
    }

    // TODO 获取时序库中状态信息
}
