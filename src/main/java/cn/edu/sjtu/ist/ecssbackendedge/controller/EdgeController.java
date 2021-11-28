package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.service.EdgeService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/edge")
public class EdgeController {

    @Autowired
    private EdgeService edgeService;

    /**
     * 开启边缘端数据收集的功能
     * @param scheduler 调度器，间隔
     * @return 成功/失败
     */
    @PostMapping("/start")
    public Result<?> startEdge(@RequestBody CollectScheduler scheduler) {
        return ResultUtil.success(edgeService.startEdge(scheduler));
    }

    /**
     * 关闭边缘端数据收集的功能
     * @return 成功/失败
     */
    @PostMapping("/stop")
    public Result<?> stopEdge() {
        return ResultUtil.success(edgeService.stopEdge());
    }

    /**
     * 重启边缘端数据收集的功能
     * @param scheduler 调度器，间隔
     * @return 成功/失败
     */
    @PostMapping("/restart")
    public Result<?> restartEdge(@RequestBody CollectScheduler scheduler) {
        return ResultUtil.success(edgeService.restartEdge(scheduler));
    }


}
