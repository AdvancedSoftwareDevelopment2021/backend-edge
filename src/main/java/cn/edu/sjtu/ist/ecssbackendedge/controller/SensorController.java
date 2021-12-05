package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.SensorRequest;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.response.SensorResponse;
import cn.edu.sjtu.ist.ecssbackendedge.service.SensorService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备的sensor相关
 * @date 2021-11-17
 */
@RestController
@RequestMapping(value = "/sensor")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping(value = "/{id}")
    public Result<SensorResponse> insertSensor(@PathVariable String id, @RequestBody SensorRequest request) {
        return ResultUtil.success(sensorService.createSensor(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public Result deleteSensor(@PathVariable String id) {
        sensorService.deleteSensorById(id);
        return ResultUtil.success();
    }

    @DeleteMapping(value = "/device/{id}")
    public Result deleteSensors(@PathVariable String id) {
        sensorService.deleteSensorsByDeviceId(id);
        return ResultUtil.success();
    }

//    @PutMapping(value = "")
//    public ResponseEntity<?> updateDevice(@RequestParam(value = "id") String id, @RequestBody DeviceDTO deviceDTO) {
//        return new ResponseEntity<>(deviceProtocolService.updateDevice(id, deviceDTO), HttpStatus.OK);
//    }

    @GetMapping(value = "/{id}")
    public Result<SensorResponse> getSensorById(@PathVariable String id) {
        return ResultUtil.success(sensorService.getSensorById(id));
    }

    @GetMapping(value = "/device/{id}")
    public Result<List<SensorResponse>> getSensorsByDeviceId(@PathVariable String id) {
        return ResultUtil.success(sensorService.getSensorsByDeviceId(id));
    }
}
