package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.service.EdgeService;
import cn.edu.sjtu.ist.ecssbackendedge.model.process.Process;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/edge")
public class EdgeController {

    @Autowired
    private EdgeService edgeService;

    /**
     * 关闭边缘端数据收集的功能
     * @return 成功/失败
     */
    @PostMapping("/stop")
    public ResponseEntity<?> stopEdge() {
        return new ResponseEntity<>(edgeService.stopEdge(), HttpStatus.OK);
    }

    /**
     * 重启边缘端数据收集的功能
     * @param scheduler 调度器，间隔
     * @return 成功/失败
     */
    @PostMapping("/restart")
    public ResponseEntity<?> restartEdge(@RequestBody CollectScheduler scheduler) {
        return new ResponseEntity<>(edgeService.restartEdge(scheduler), HttpStatus.OK);
    }

    /**
     * 接收并保存云端的url，默认直接开启
     * @param map 云url, 边id, interval, timeUnit
     * @return 接收情况
     */
    @PostMapping("")
    public ResponseEntity<?> receiveCloudUrl(@RequestBody Map<String, String> map) {
        return new ResponseEntity<>(edgeService.setCloudUrl(map), HttpStatus.OK);
    }

    /**
     * 接收下发的流程
     * @param process 流程
     * @return true/false
     */
    @PostMapping("/process")
    public ResponseEntity<?> receiveCloudProcess(@RequestBody Process process) {
        return new ResponseEntity<>(edgeService.processControl(process), HttpStatus.OK);
    }
}
