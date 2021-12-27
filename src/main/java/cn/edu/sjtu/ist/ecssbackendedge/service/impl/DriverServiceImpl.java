package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DriverDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.Command;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.driver.Driver;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.driver.DriverDTO;
import cn.edu.sjtu.ist.ecssbackendedge.service.DriverService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DriverUtil;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dyanjun
 * @date 2021/12/26 0:33
 */
@Service
@Slf4j
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverDao driverDao;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private DriverUtil driverUtil;

    @Override
    public DriverDTO addDriver(DriverDTO driverDTO) {
        String deviceId = driverDTO.getDeviceId();
        Driver driver = driverDao.findDriverByDeviceIDAndName(deviceId, driverDTO.getName());
        if (driver != null) {
            throw new RuntimeException("创建driver名称" + driverDTO.getName() + "失败，同名driver已存在");
        }
        // 把driverId装配进device
        Device device = deviceDao.findDeviceById(deviceId);
        for (Command command : device.getCommands()) {
            if (command.getName().equals(driverDTO.getName())) {
                // 创建Driver
                driver = driverUtil.convertDTO2Domain(driverDTO);
                driver = driverDao.createDriver(driver);
                command.setDriverId(driver.getId());
                break;
            }
        }
        deviceDao.modifyDevice(device);
        return driverUtil.convertDomain2DTO(driver);
    }

    @Override
    public void updateDriver(DriverDTO driverDTO) {
        Driver driver = driverDao.findDriverById(driverDTO.getId());
        if (driver == null) throw new RuntimeException("driver不存在");
        driverDao.modifyDriver(driver);
    }

    @Override
    public List<DriverDTO> getDriverByDevice(String id) {
        List<Driver> driverList = driverDao.findDriverByDeviceId(id);
        List<DriverDTO> driverDTOList = new ArrayList<>();
        for (Driver driver : driverList) {
            DriverDTO driverDTO = driverUtil.convertDomain2DTO(driver);
            driverDTOList.add(driverDTO);
        }
        return driverDTOList;
    }

    @Override
    public void executeCommandByDriverId(String id) {
        Driver driver = driverDao.findDriverById(id);
        Device device = deviceDao.findDeviceById(driver.getDeviceId());
        List<Command> commands = device.getCommands();
        for (Command command : commands) {
            if (command.getDriverId() != null && command.getDriverId().equals(driver.getId())) {
                driver.executeCommand(command.getCommandType(), command.getAsy(), command.getType(), command.getValue(), command.getParams());
            }
        }
    }

    @Override
    public void commandFeedBack(String id, Result result) {
        log.info("driver:" + id + "异步回调, " + result.getMessage());
        // TODO 判断是否成功，进行流程的推进
        // TODO 记录命令的执行结果
    }
}
