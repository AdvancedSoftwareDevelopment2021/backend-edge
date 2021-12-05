package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.service.SensorService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author rsp
 * @version 0.1
 * @brief sensor指令
 * @date 2021-11-18
 */
@RestController
@RequestMapping(value = "/command")
public class CommandController {

    @Autowired
    private SensorService sensorService;

    /**
     * 开启某个设备的sensor
     *
     * @param id       设备id
     * @param sensorId sensor id
     * @return 成功/失败
     */
    @PostMapping(value = "/sensor/start/{id}/{sensorId}")
    public Result startSensor(@PathVariable String id, @PathVariable String sensorId) {
        sensorService.startSensor(id, sensorId);
        return ResultUtil.success();
    }

    /**
     * 关闭某个设备的sensor
     *
     * @param id       设备id
     * @param sensorId sensor id
     * @return 成功/失败
     */
    @PostMapping(value = "/sensor/stop/{id}/{sensorId}")
    public Result stopSensor(@PathVariable String id, @PathVariable String sensorId) {
        sensorService.stopSensor(id, sensorId);
        return ResultUtil.success();
    }

    @PostMapping(value = "/start_monitor/{id}/{sensorId}")
    public Result startMonitorSensor(@PathVariable String id, @PathVariable String sensorId) {
        sensorService.startMonitor(id, sensorId);
        return ResultUtil.success();
    }

    @PostMapping(value = "/stop_monitor/{id}/{sensorId}")
    public Result stopMonitorSensor(@PathVariable String id, @PathVariable String sensorId) {
        sensorService.stopMonitor(id, sensorId);
        return ResultUtil.success();
    }
}
