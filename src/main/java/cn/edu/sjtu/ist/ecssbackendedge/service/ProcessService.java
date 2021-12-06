package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.ProcessDTO;

import java.util.List;

public interface ProcessService {

    ProcessDTO insertProcess(ProcessDTO processDTO);

    void deleteProcess(String id);

    void updateProcess(String id, ProcessDTO processDTO);

    ProcessDTO getProcess(String id);

    List<ProcessDTO> getAllProcesses();

}
