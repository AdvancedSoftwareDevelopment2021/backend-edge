package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceData;
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

    // test
    @GetMapping(value = "/hello")
    public ResponseEntity<?> getDevice() {
        return new ResponseEntity<>("hello, device data!", HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> insertDeviceData(@RequestBody DeviceData deviceData) {
        return new ResponseEntity<>(deviceDataService.insertDeviceData(deviceData), HttpStatus.OK);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> deleteDeviceData(@RequestParam(value = "id") Long id) {
        return new ResponseEntity<>(deviceDataService.deleteDeviceData(id), HttpStatus.OK);
    }

    @PutMapping(value = "")
    public ResponseEntity<?> updateDeviceData(@RequestParam(value = "id") Long id, @RequestBody DeviceData deviceData) {
        return new ResponseEntity<>(deviceDataService.updateDeviceData(id, deviceData), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getDeviceData(@RequestParam(value = "id") Long id) {
        return new ResponseEntity<>(deviceDataService.getDeviceData(id), HttpStatus.OK);
    }
}