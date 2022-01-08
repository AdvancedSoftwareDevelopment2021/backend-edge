package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.Command;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.driver.DriverDTO;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;

import java.util.*;
/**
 * @author dyanjun
 * @date 2021/12/26 0:33
 */
public interface DriverService {
    DriverDTO addDriver(DriverDTO driverDTO);

    void updateDriver(DriverDTO driverDTO);

    List<DriverDTO> getDriverByDevice(String id);

    void commandFeedBack(String id, Result result);

    void deleteDriver(String id);

    DriverDTO getDriverById(String id);
}
