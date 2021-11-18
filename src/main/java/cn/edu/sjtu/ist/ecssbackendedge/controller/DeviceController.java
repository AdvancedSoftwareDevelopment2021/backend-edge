package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDTO;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @brief 设备 Controller
 * @author rsp
 * @version 0.1
 * @date 2021-10-23
 */
@Slf4j
@RestController
@RequestMapping(value = "/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    // test
    @GetMapping(value = "/hello")
    public ResponseEntity<?> getDevice() {
        return new ResponseEntity<>("hello, device!", HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> insertDevice(@RequestBody DeviceDTO deviceDTO) {
        System.out.println(deviceDTO);
        return new ResponseEntity<>(deviceService.insertDevice(deviceDTO), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteDevice(@PathVariable String id) {
        return new ResponseEntity<>(deviceService.deleteDevice(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDevice(@PathVariable String id, @RequestBody DeviceDTO deviceDTO) {
        return new ResponseEntity<>(deviceService.updateDevice(id, deviceDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getDevice(@PathVariable String id) {
        return new ResponseEntity<>(deviceService.getDevice(id), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getAllDevices() {
        return new ResponseEntity<>(deviceService.getAllDevices(), HttpStatus.OK);
    }
}
