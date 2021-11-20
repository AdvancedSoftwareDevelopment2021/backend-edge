package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.service.EdgeService;
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

    @Autowired
    private EdgeService edgeService;

    /**
     * 开启某个设备的sensor
     * @param id 设备id
     * @param sensorId sensor id
     * @return 成功/失败
     */
    @PostMapping(value = "/sensor/start/{id}/{sensorId}")
    public Result<?> startSensor(@PathVariable String id, @PathVariable String sensorId) {
        return ResultUtil.success(sensorService.startSensor(id, sensorId));
    }

    /**
     * 关闭某个设备的sensor
     * @param id 设备id
     * @param sensorId sensor id
     * @return 成功/失败
     */
    @PostMapping(value = "/sensor/stop/{id}/{sensorId}")
    public Result<?> stopSensor(@PathVariable String id, @PathVariable String sensorId) {
        return ResultUtil.success(sensorService.stopSensor(id, sensorId));
    }

    /**
     * 开启边缘端数据收集的功能
     * @param scheduler 调度器，间隔
     * @return 成功/失败
     */
    @PostMapping("/edge/start")
    public Result<?> startEdge(@RequestBody CollectScheduler scheduler) {
        return ResultUtil.success(edgeService.startEdge(scheduler));
    }

    /**
     * 关闭边缘端数据收集的功能
     * @return 成功/失败
     */
    @PostMapping("/edge/stop")
    public Result<?> stopEdge() {
        return ResultUtil.success(edgeService.stopEdge());
    }

    /**
     * 重启边缘端数据收集的功能
     * @param scheduler 调度器，间隔
     * @return 成功/失败
     */
    @PostMapping("/edge/restart")
    public Result<?> restartEdge(@RequestBody CollectScheduler scheduler) {
        return ResultUtil.success(edgeService.restartEdge(scheduler));
    }
}
