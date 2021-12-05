package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.ProcessDTO;
import cn.edu.sjtu.ist.ecssbackendedge.service.ProcessService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/process")
public class ProcecssController {

    @Autowired
    private ProcessService processService;

    @PostMapping(value = "")
    public Result<ProcessDTO> insertProcess(@RequestBody ProcessDTO dto) {
        return ResultUtil.success(processService.insertProcess(dto));
    }

    @DeleteMapping(value = "/{id}")
    public Result deleteProcess(@PathVariable String id) {
        processService.deleteProcess(id);
        return ResultUtil.success();
    }

    @PutMapping(value = "/{id}")
    public Result updateProcess(@PathVariable String id, @RequestBody ProcessDTO dto) {
        processService.updateProcess(id, dto);
        return ResultUtil.success();
    }

    @GetMapping(value = "/{id}")
    public Result<ProcessDTO> getProcess(@PathVariable String id) {
        return ResultUtil.success(processService.getProcess(id));
    }

    @GetMapping(value = "")
    public Result<List<ProcessDTO>> getAllProcesses() {
        return ResultUtil.success(processService.getAllProcesses());
    }
}
