package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.device.DeviceDTO;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备 Controller
 * @date 2021-10-23
 */
@Slf4j
@RestController
@RequestMapping(value = "/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping(value = "")
    public Result<DeviceDTO> insertDevice(@RequestBody DeviceDTO deviceDTO) {
        return ResultUtil.success(deviceService.insertDevice(deviceDTO));
    }

    @DeleteMapping(value = "/{id}")
    public Result deleteDevice(@PathVariable String id) {
        deviceService.deleteDevice(id);
        return ResultUtil.success();
    }

    @PutMapping(value = "/{id}")
    public Result<DeviceDTO> updateDevice(@PathVariable String id, @RequestBody DeviceDTO deviceDTO) {
        return ResultUtil.success(deviceService.updateDevice(id, deviceDTO));
    }

    @GetMapping(value = "/{id}")
    public Result<DeviceDTO> getDevice(@PathVariable String id) {
        return ResultUtil.success(deviceService.getDevice(id));
    }

    @GetMapping(value = "")
    public Result<List<DeviceDTO>> getAllDevices() {
        return ResultUtil.success(deviceService.getAllDevices());
    }
}
