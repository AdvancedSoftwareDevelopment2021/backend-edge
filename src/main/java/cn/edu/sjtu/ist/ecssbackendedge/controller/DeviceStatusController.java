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
    public ResponseEntity<?> insertDeviceStatus(@RequestBody DeviceStatusDTO deviceStatusDTO) {
        return new ResponseEntity<>(deviceStatusService.insertDeviceStatus(deviceStatusDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/history/{id}")
    public ResponseEntity<?> deleteDeviceAllHistoryStatus(@PathVariable String id) {
        return new ResponseEntity<>(deviceStatusService.deleteDeviceAllHistoryStatus(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDevice(@PathVariable String id, @RequestBody DeviceStatusDTO deviceStatusDTO) {
        return new ResponseEntity<>(deviceStatusService.updateDeviceStatus(id, deviceStatusDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/latest/{id}/{sensorName}")
    public ResponseEntity<?> getLatestDeviceStatus(@PathVariable String id, @PathVariable String sensorName) {
        return new ResponseEntity<>(deviceStatusService.getLatestDeviceStatus(id, sensorName), HttpStatus.OK);
    }

    @GetMapping(value = "/history/{id}/{sensorName}")
    public ResponseEntity<?> getDeviceHistoryStatus(@PathVariable String id, @PathVariable String sensorName,
            @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize, @RequestParam("filters") String filters) {
        return new ResponseEntity<>(deviceStatusService.getDeviceHistoryStatus(id, sensorName, filters, pageIndex, pageSize), HttpStatus.OK);
    }
}
