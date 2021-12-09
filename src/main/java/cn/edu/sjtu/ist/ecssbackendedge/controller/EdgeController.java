package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.response.CommandResponse;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.Process;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.service.EdgeService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/edge")
public class EdgeController {

    @Autowired
    private EdgeService edgeService;

    /**
     * 关闭边缘端数据收集的功能
     *
     * @return 成功/失败
     */
    @PostMapping("/stop")
    public Result<CommandResponse> stopEdge() {
        return ResultUtil.success(edgeService.stopEdge());
    }

    /**
     * 重启边缘端数据收集的功能
     *
     * @param scheduler 调度器，间隔
     * @return 成功/失败
     */
    @PostMapping("/restart")
    public  Result<CommandResponse> restartEdge(@RequestBody CollectScheduler scheduler) {
        return ResultUtil.success(edgeService.restartEdge(scheduler));
    }

    /**
     * 接收并保存云端的url，默认直接开启
     *
     * @param map 云url, 边id, interval, timeUnit
     * @return 接收情况
     */
    @PostMapping("")
    public  Result<CommandResponse> receiveCloudUrl(@RequestBody Map<String, String> map) {
        return ResultUtil.success(edgeService.setCloudUrl(map));
    }

    /**
     * 接收下发的流程
     *
     * @param process 流程
     * @return true/false
     */
    @PostMapping("/process")
    public Result<CommandResponse> receiveCloudProcess(@RequestBody Process process) {
        return ResultUtil.success(edgeService.processControl(process));
    }
}
