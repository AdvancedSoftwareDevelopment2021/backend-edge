package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.device.DeviceDataDTO;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceDataService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备数据 Controller
 * @date 2021-10-25
 */
@RestController
@RequestMapping("/device/data")
public class DeviceDataController {

    @Autowired
    private DeviceDataService deviceDataService;

    @PostMapping(value = "")
    public Result insertDeviceData(@RequestBody DeviceDataDTO deviceDataDTO) {
        deviceDataService.insertDeviceData(deviceDataDTO);
        return ResultUtil.success();
    }

    @DeleteMapping(value = "/history/{id}")
    public Result deleteDeviceData(@PathVariable String id) {
        deviceDataService.deleteDeviceAllHistoryData(id);
        return ResultUtil.success();
    }

    @PutMapping(value = "/{id}")
    public Result updateDeviceData(@PathVariable String id, @RequestBody DeviceDataDTO deviceDataDTO) {
        return ResultUtil.success();
    }

    @GetMapping(value = "/latest/{id}/{sensorName}")
    public Result<DeviceDataDTO> getDeviceData(@PathVariable String id, @PathVariable String sensorName) {
        return ResultUtil.success(deviceDataService.getLatestDeviceData(id, sensorName));
    }

    @GetMapping(value = "/{id}/{sensorName}")
    public Result<List<DeviceDataDTO>> getDeviceAllHistoryData(@PathVariable String id, @PathVariable String sensorName) {
        return ResultUtil.success(deviceDataService.getDeviceAllHistoryData(id, sensorName));
    }

    @GetMapping(value = "/history/{id}/{sensorName}")
    public Result<List<DeviceDataDTO>> getDeviceHistoryData(@PathVariable String id, @PathVariable String sensorName,
                                                            @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize, @RequestParam("filters") String filters) {
        return ResultUtil.success(deviceDataService.getDeviceHistoryData(id, sensorName, filters, pageIndex, pageSize));
    }
}
