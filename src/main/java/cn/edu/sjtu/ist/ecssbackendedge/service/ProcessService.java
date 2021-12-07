package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.process.TaskWithDeviceDTO;
import cn.edu.sjtu.ist.ecssbackendedge.model.process.Process;

import java.util.List;

public interface ProcessService {

    Process insertProcess(Process process);

    void deleteProcess(String processId);

    void updateProcess(String processId, Process process);

    void updateProcessName(String processId, String name);

    Process getProcess(String processId);

    List<Process> getAllProcesses();

    String findBpmn(String processId);

    void updateProcessBpmn(String processId, String bpmn);

    void bindDevice(String processId, String taskId, String deviceId);

    void deleteDevice(String processId, String taskId);

    List<TaskWithDeviceDTO> findAllDevices(String processId);
}
