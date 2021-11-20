package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDataDTO;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @brief 设备数据 Controller
 * @author rsp
 * @version 0.1
 * @date 2021-10-25
 */
@RestController
@RequestMapping("/device/data")
public class DeviceDataController {

    @Autowired
    private DeviceDataService deviceDataService;

    @PostMapping(value = "")
    public ResponseEntity<?> insertDeviceData(@RequestBody DeviceDataDTO deviceDataDTO) {
        return new ResponseEntity<>(deviceDataService.insertDeviceData(deviceDataDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/history/{id}")
    public ResponseEntity<?> deleteDeviceData(@PathVariable String id) {
        return new ResponseEntity<>(deviceDataService.deleteDeviceAllHistoryData(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDeviceData(@PathVariable String id, @RequestBody DeviceDataDTO deviceDataDTO) {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/{sensorName}")
    public ResponseEntity<?> getDeviceData(@PathVariable String id, @PathVariable String sensorName) {
        return new ResponseEntity<>(deviceDataService.getLatestDeviceData(id, sensorName), HttpStatus.OK);
    }

    @GetMapping(value = "/history/{id}/{sensorName}")
    public ResponseEntity<?> getDeviceHistoryData(@PathVariable String id, @PathVariable String sensorName,
            @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize, @RequestParam("filters") String filters) {
        return new ResponseEntity<>(deviceDataService.getDeviceHistoryData(id, sensorName, filters, pageIndex, pageSize), HttpStatus.OK);
    }
}
