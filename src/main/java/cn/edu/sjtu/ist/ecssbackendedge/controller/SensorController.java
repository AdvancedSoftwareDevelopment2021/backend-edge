package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.collecting.SensorRequest;
import cn.edu.sjtu.ist.ecssbackendedge.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @brief 设备的sensor相关
 * @author rsp
 * @version 0.1
 * @date 2021-11-17
 */
@RestController
@RequestMapping(value = "/sensor")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    // test
    @GetMapping(value = "/hello")
    public ResponseEntity<?> getDevice() {
        return new ResponseEntity<>("hello, sensor!", HttpStatus.OK);
    }

    @PostMapping(value = "/{id}")
    public ResponseEntity<?> insertSensor(@PathVariable String id, @RequestBody SensorRequest request) {
        return new ResponseEntity<>(sensorService.createSensor(id, request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSensor(@PathVariable String id) {
        return new ResponseEntity<>(sensorService.deleteSensorById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/device/{id}")
    public ResponseEntity<?> deleteSensors(@PathVariable String id) {
        return new ResponseEntity<>(sensorService.deleteSensorsByDeviceId(id), HttpStatus.OK);
    }

//    @PutMapping(value = "")
//    public ResponseEntity<?> updateDevice(@RequestParam(value = "id") String id, @RequestBody DeviceDTO deviceDTO) {
//        return new ResponseEntity<>(deviceProtocolService.updateDevice(id, deviceDTO), HttpStatus.OK);
//    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getSensorById(@PathVariable String id) {
        return new ResponseEntity<>(sensorService.getSensorById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/device/{id}")
    public ResponseEntity<?> getSensorsByDeviceId(@PathVariable String id) {
        return new ResponseEntity<>(sensorService.getSensorsByDeviceId(id), HttpStatus.OK);
    }
}
