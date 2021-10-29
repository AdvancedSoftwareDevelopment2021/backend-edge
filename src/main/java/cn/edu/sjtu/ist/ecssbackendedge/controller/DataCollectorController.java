package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.DataCollector;
import cn.edu.sjtu.ist.ecssbackendedge.service.DataCollectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author dyanjun
 * @date 2021/10/29 15:15
 */
@Controller
@RequestMapping(value = "/data_collector")
public class DataCollectorController {
    @Autowired
    private DataCollectorService dataCollectorService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDataCollector(@PathVariable String id) {
        return new ResponseEntity<>(dataCollectorService.getDataCollectorById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createDataCollector(@RequestBody DataCollector dataCollector) {
        dataCollectorService.insertDataCollector(dataCollector);
        return new ResponseEntity<>("create success", HttpStatus.OK);
    }
}
