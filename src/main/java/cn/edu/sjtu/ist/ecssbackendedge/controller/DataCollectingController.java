package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.collecting.AddDataCollectingRequest;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collecting.DataCollectingPO;
import cn.edu.sjtu.ist.ecssbackendedge.service.DataCollectingService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author dyanjun
 * @date 2021/10/29 15:15
 */
@RestController
@RequestMapping(value = "/data_collector")
public class DataCollectingController {

    @Resource
    private DataCollectingService dataCollectingService;

    @GetMapping("/{id}")
    public Result<DataCollectingPO> getDataCollector(@PathVariable String id) {
        return ResultUtil.success(dataCollectingService.getDataCollectingById(id));
    }

    @PostMapping
    public Result<DataCollectingPO> createDataCollector(@RequestBody AddDataCollectingRequest request) {
        return ResultUtil.success(dataCollectingService.insertDataCollecting(request));
    }
}
