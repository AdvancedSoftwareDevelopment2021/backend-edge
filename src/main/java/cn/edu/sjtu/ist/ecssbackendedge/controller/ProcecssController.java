package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.ProcessDTO;
import cn.edu.sjtu.ist.ecssbackendedge.service.ProcessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/process")
public class ProcecssController {

    @Autowired
    private ProcessService processService;
    
    @PostMapping(value = "")
    public ResponseEntity<?> insertProcess(@RequestBody ProcessDTO dto) {
        return new ResponseEntity<>(processService.insertProcess(dto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProcess(@PathVariable String id) {
        return new ResponseEntity<>(processService.deleteProcess(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateProcess(@PathVariable String id, @RequestBody ProcessDTO dto) {
        return new ResponseEntity<>(processService.updateProcess(id, dto), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProcess(@PathVariable String id) {
        return new ResponseEntity<>(processService.getProcess(id), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getAllProcesses() {
        return new ResponseEntity<>(processService.getAllProcesses(), HttpStatus.OK);
    }
}
