package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.driver.DriverDTO;
import cn.edu.sjtu.ist.ecssbackendedge.service.DriverService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/26 0:31
 */
@Slf4j
@RestController
@RequestMapping(value = "/driver")
public class DriverController {

    @Autowired
    DriverService driverService;

    @PostMapping(value = "")
    public Result<DriverDTO> addDriver(@RequestBody DriverDTO driverDTO) {
        return ResultUtil.success(driverService.addDriver(driverDTO));
    }

    @PutMapping(value = "")
    public Result updateDriver(@RequestBody DriverDTO driverDTO) {
        driverService.updateDriver(driverDTO);
        return ResultUtil.success();
    }

    @GetMapping(value = "/{id}")
    public Result<List<DriverDTO>> getDriverByDevice(@PathVariable String id) {
        return ResultUtil.success(driverService.getDriverByDevice(id));
    }

    /**
     * 用于测试根据driverId 执行命令
     * @param id
     * @return
     */
    @PostMapping(value = "/{id}")
    public Result executeCommand(@PathVariable String id) {
        driverService.executeCommandByDriverId(id);
        return ResultUtil.success();
    }

    @PostMapping(value = "/response/{id}")
    public Result commandFeedBack(@PathVariable String id, @RequestBody Result result){
        driverService.commandFeedBack(id, result);
        return ResultUtil.success();
    }

}