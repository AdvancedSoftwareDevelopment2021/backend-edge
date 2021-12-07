package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.device.DeviceStatusDTO;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceStatusService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/device/status")
public class DeviceStatusController {

    @Autowired
    private DeviceStatusService deviceStatusService;

    @PostMapping(value = "")
    public Result insertDeviceStatus(@RequestBody DeviceStatusDTO deviceStatusDTO) {
        deviceStatusService.insertDeviceStatus(deviceStatusDTO);
        return ResultUtil.success();
    }

    @DeleteMapping(value = "/history/{id}")
    public Result deleteDeviceAllHistoryStatus(@PathVariable String id) {
        deviceStatusService.deleteDeviceAllHistoryStatus(id);
        return ResultUtil.success();
    }

    @PutMapping(value = "/{id}")
    public Result updateDevice(@PathVariable String id, @RequestBody DeviceStatusDTO deviceStatusDTO) {
        deviceStatusService.updateDeviceStatus(id, deviceStatusDTO);
        return ResultUtil.success();
    }

    @GetMapping(value = "/latest/{id}/{sensorName}")
    public Result<DeviceStatusDTO> getLatestDeviceStatus(@PathVariable String id, @PathVariable String sensorName) {
        return ResultUtil.success(deviceStatusService.getLatestDeviceStatus(id, sensorName));
    }

    @GetMapping(value = "/history/{id}/{sensorName}")
    public Result<List<DeviceStatusDTO>> getDeviceHistoryStatus(@PathVariable String id, @PathVariable String sensorName,
                                                                @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize, @RequestParam("filters") String filters) {
        return ResultUtil.success(deviceStatusService.getDeviceHistoryStatus(id, sensorName, filters, pageIndex, pageSize));
    }
}
