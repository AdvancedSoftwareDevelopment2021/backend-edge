package cn.edu.sjtu.ist.ecssbackendedge.controller;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.process.ProcessDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.Process;
import cn.edu.sjtu.ist.ecssbackendedge.service.ProcessService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.ProcessUtil;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/process")
public class ProcecssController {

    @Autowired
    private ProcessService processService;

    @Autowired
    private ProcessUtil processUtil;

    /**
     * 边缘端新建流程，云端调用接口
     * @param dto 流程相关信息
     * @return 流程详细信息
     */
    @PostMapping(value = "")
    public Result<?> insertProcess(@RequestBody ProcessDTO dto) {
        Process process = processUtil.convertDTO2Domain(dto);
        return ResultUtil.success(processService.insertProcess(process));
    }

    /**
     * 删除流程
     * @param id 流程id
     * @return 无
     */
    @DeleteMapping(value = "/{id}")
    public Result<?> deleteProcess(@PathVariable String id) {
        processService.deleteProcess(id);
        return ResultUtil.success();
    }

    /**
     * 修改流程名称
     * @param id 流程id
     * @param name 新名称
     * @return 无
     */
    @PutMapping(value = "/{id}/name/{name}")
    public Result<?> updateProcessName(@PathVariable String id, @PathVariable String name) {
        processService.updateProcessName(id, name);
        return ResultUtil.success();
    }

    /**
     * 获取单个流程
     * @param id 流程id
     * @return 流程
     */
    @GetMapping(value = "/{id}")
    public Result<?> getProcess(@PathVariable String id) {
        return ResultUtil.success(processService.getProcess(id));
    }

    /**
     * 获取所有流程
     * @return 所有流程
     */
    @GetMapping(value = "")
    public Result<?> getAllProcesses() {
        return ResultUtil.success(processService.getAllProcesses());
    }

    /**
     * 开启流程
     * @param id 流程id
     * @return 无
     */
    @PostMapping(value = "/start/{id}")
    public Result<?> startProcess(@PathVariable String id) {
        Process process = processService.getProcess(id);
        if (!process.canStart()) {
            throw new RuntimeException("WRONG_STATE");
        }
        process.start();
        processService.updateProcess(id, process);
        return ResultUtil.success();
    }

    /**
     * 关闭流程
     * @param id 流程id
     * @return 无
     */
    @PostMapping(value = "/stop/{id}")
    public Result<?> stopProcess(@PathVariable String id) {
        Process process = processService.getProcess(id);
        if (!process.canStop()) {
            throw new RuntimeException("WRONG_STATE");
        }
        process.stop();
        processService.updateProcess(id, process);
        return ResultUtil.success();
    }

    /**
     * 获取流程的bpmn文件内容
     * @param id 流程id
     * @return bpmn文件内容（String形式)
     */
    @GetMapping("/bpmn/{id}")
    public Result<String> findBpmn(@PathVariable String id) {
        return ResultUtil.success(processService.findBpmn(id));
    }

    /**
     * 绑定设备到流程的某个task节点
     * @param id 流程id
     * @param taskId taskId
     * @param deviceId 设备id
     * @return 无
     */
    @PostMapping("/device/{id}/{taskId}/{deviceId}")
    public Result<?> bindServiceForProcess(@PathVariable String id,
                                           @PathVariable String taskId,
                                           @PathVariable String deviceId) {
        processService.bindDevice(id, taskId, deviceId);
        return ResultUtil.success();
    }

    /**
     * 删除某个流程的task上绑定的设备
     * @param id 流程id
     * @param taskId task id
     * @return 无
     */
    @DeleteMapping("/device/{id}/{taskId}")
    public Result<?> deleteDeviceForProcess(@PathVariable String id,
                                            @PathVariable String taskId) {
        processService.deleteDevice(id, taskId);
        return ResultUtil.success();
    }

    /**
     * 获取流程上所有绑定的设备信息
     * @param id 流程id
     * @return 设备信息列表（目前主要只有id）
     */
    @GetMapping("/device/{id}")
    public Result<?> findAllDeviceFromProcess(@PathVariable String id) {
        return ResultUtil.success(processService.findAllDevices(id));
    }
}
