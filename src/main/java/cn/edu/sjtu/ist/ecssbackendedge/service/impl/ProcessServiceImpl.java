package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.ProcessDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.Element;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.Step;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.process.TaskWithDeviceDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.Process;
import cn.edu.sjtu.ist.ecssbackendedge.service.ProcessService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.process.BpmnUtils;

import cn.edu.sjtu.ist.ecssbackendedge.utils.response.JsonUtil;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.ElementType.DEVICE_KEY;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessDao processDao;

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public Process insertProcess(Process process) {
        return processDao.createProcess(process);
    }

    @Override
    public void deleteProcess(String id) {
        processDao.removeProcess(id);
    }

    @Override
    public void updateProcess(String processId, Process process) {
        process.setId(processId);
        processDao.modifyProcess(process);
    }

    @Override
    public void updateProcessName(String processId, String name) {
        Process process = processDao.findProcessById(processId);
        process.setName(name);
        processDao.modifyProcess(process);
    }

    @Override
    public void updateProcessStep(String processId, Step step) {
        Process process = processDao.findProcessById(processId);
        process.setStep(step);
        processDao.modifyProcess(process);
    }

    @Override
    public Process getProcess(String processId) {
        return processDao.findProcessById(processId);
    }

    @Override
    public List<Process> getAllProcesses() {
        return processDao.findAllProcesss();
    }

    @Override
    public String findBpmn(String processId) {
        Process process = processDao.findProcessById(processId);
        return process.getBpmn();
    }

    @Override
    public void updateProcessBpmn(String processId, String bpmn) {
        Process process = processDao.findProcessById(processId);
        process.setBpmn(bpmn);
        processDao.modifyProcess(process);
    }

    @Override
    public void bindDevice(String processId, String taskId, String deviceId) {
        Process process = processDao.findProcessById(processId);
        Device device = deviceDao.findDeviceById(deviceId);
        BpmnModelInstance instance = Bpmn.readModelFromStream(BpmnUtils.strToInStream(process.getBpmn()));
        BpmnModelInstance newInstance = BpmnUtils.setExtension(instance, taskId, DEVICE_KEY.getKey(), JsonUtil.writeValueAsString(device));
        updateProcessBpmn(processId, BpmnUtils.bpmnInstToStr(newInstance));
    }

    @Override
    public void deleteDevice(String processId, String taskId) {
        Process process = processDao.findProcessById(processId);
        BpmnModelInstance instance = Bpmn.readModelFromStream(BpmnUtils.strToInStream(process.getBpmn()));
        BpmnModelInstance newInstance = BpmnUtils.deleteExtension(instance, taskId, DEVICE_KEY.getKey());
        updateProcessBpmn(processId, BpmnUtils.bpmnInstToStr(newInstance));
    }

    @Override
    public List<TaskWithDeviceDTO> findAllDevices(String processId) {
        Process process = processDao.findProcessById(processId);
        BpmnModelInstance instance = Bpmn.readModelFromStream(BpmnUtils.strToInStream(process.getBpmn()));
        List<Element> elementDTOS = BpmnUtils.getExtensionByType(instance, DEVICE_KEY);
        List<TaskWithDeviceDTO> taskWithDeviceDTOS = new LinkedList<>();
        for (Element elementDTO: elementDTOS) {
            if (elementDTO.getValue() == null){
                taskWithDeviceDTOS.add(TaskWithDeviceDTO.builder()
                        .taskId(elementDTO.getElementId())
                        .taskName(elementDTO.getElementName())
                        .deviceId(null)
//                        .metadataId(null)
                        .build());
            }
            else {
                String deviceId = elementDTO.getValue();
                taskWithDeviceDTOS.add(TaskWithDeviceDTO.builder()
                        .taskId(elementDTO.getElementId())
                        .taskName(elementDTO.getElementName())
                        .deviceId(deviceId)
//                        .metadataId(null)
                        .build());
            }
        }
        return taskWithDeviceDTOS;
    }
}
