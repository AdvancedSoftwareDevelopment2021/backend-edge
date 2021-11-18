package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.service.SensorService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @brief sensor指令
 * @author rsp
 * @version 0.1
 * @date 2021-11-18
 */
@RestController
@RequestMapping(value = "/command")
public class CommandController {

    @Autowired
    private SensorService sensorService;

    @PostMapping(value = "/start/{id}/{sensorId}")
    public Result<?> startSensor(@PathVariable String id, @PathVariable String sensorId) {
        return ResultUtil.success(sensorService.startSensor(id, sensorId));
    }

    @PostMapping(value = "/stop/{id}/{sensorId}")
    public Result<?> stopSensor(@PathVariable String id, @PathVariable String sensorId) {
        return ResultUtil.success(sensorService.stopSensor(id, sensorId));
    }
}
