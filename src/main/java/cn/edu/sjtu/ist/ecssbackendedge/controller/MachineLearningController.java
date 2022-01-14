package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.CommandData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.machineLearning.MachineLearning;
import cn.edu.sjtu.ist.ecssbackendedge.service.MachineLearningService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author dyanjun
 * @date 2022/1/14 0:34
 */
@Slf4j
@RestController
@RequestMapping(value = "/ml")
public class MachineLearningController {
    @Autowired
    MachineLearningService machineLearningService;

    @GetMapping("")
    public Result<List<MachineLearning>> getAllMachineLearningModal() {
        return ResultUtil.success(machineLearningService.getAllMachineLearning());
    }

    @PostMapping("/{name}")
    public Result receiveModel(@PathVariable String name){
        machineLearningService.createMachineLearning(name);
        return ResultUtil.success();
    }

    @GetMapping("/device/{deviceId}/{driverName}")
    public Result<List<CommandData>> findDeviceAllMLResult(@PathVariable String deviceId, @PathVariable String driverName) {
        return ResultUtil.success(machineLearningService.findDeviceAllMLResult(deviceId, driverName));
    }
}
