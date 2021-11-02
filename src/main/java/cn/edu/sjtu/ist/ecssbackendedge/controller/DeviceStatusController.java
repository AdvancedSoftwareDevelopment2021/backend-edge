package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceStatusDTO;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceStatusService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RestController
@RequestMapping(value = "/device/status")
public class DeviceStatusController {

    @Autowired
    private DeviceStatusService deviceStatusService;

    // test
    @GetMapping(value = "/hello")
    public ResponseEntity<?> getDevice() {
        return new ResponseEntity<>("hello, device status!", HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> insertDevice(@RequestBody DeviceStatusDTO deviceStatusDTO) {
        return new ResponseEntity<>(deviceStatusService.insertDeviceStatus(deviceStatusDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<?> deleteDevice(@RequestParam(value = "id") String id) {
        return new ResponseEntity<>(deviceStatusService.deleteDeviceStatus(id), HttpStatus.OK);
    }

    @PutMapping(value = "")
    public ResponseEntity<?> updateDevice(@RequestParam(value = "id") String id, @RequestBody DeviceStatusDTO deviceStatusDTO) {
        return new ResponseEntity<>(deviceStatusService.updateDeviceStatus(id, deviceStatusDTO), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getDevice(@RequestParam(value = "id") String id) {
        return new ResponseEntity<>(deviceStatusService.getDeviceStatus(id), HttpStatus.OK);
    }
}
