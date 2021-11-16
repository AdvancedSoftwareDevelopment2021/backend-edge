package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDTO;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceProtocolService;
import cn.edu.sjtu.ist.ecssbackendedge.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @brief 为设备添加协议，或者修改设备协议
 */
@RestController
public class DeviceProtocolController {

    @Autowired
    private DeviceProtocolService deviceProtocolService;

    // test
    @GetMapping(value = "/hello")
    public ResponseEntity<?> getDevice() {
        return new ResponseEntity<>("hello, device!", HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> insertProtocol(@RequestBody Map<String, String> request) {
        return new ResponseEntity<>(deviceProtocolService.insertProtocol(request), HttpStatus.OK);
    }

//    @DeleteMapping(value = "")
//    public ResponseEntity<?> deleteDevice(@RequestParam(value = "id") String id) {
//        return new ResponseEntity<>(deviceProtocolService.deleteDevice(id), HttpStatus.OK);
//    }
//
//    @PutMapping(value = "")
//    public ResponseEntity<?> updateDevice(@RequestParam(value = "id") String id, @RequestBody DeviceDTO deviceDTO) {
//        return new ResponseEntity<>(deviceProtocolService.updateDevice(id, deviceDTO), HttpStatus.OK);
//    }
//
//    @GetMapping(value = "")
//    public ResponseEntity<?> getDevice(@RequestParam(value = "id") String id) {
//        return new ResponseEntity<>(deviceProtocolService.getDevice(id), HttpStatus.OK);
//    }

}
