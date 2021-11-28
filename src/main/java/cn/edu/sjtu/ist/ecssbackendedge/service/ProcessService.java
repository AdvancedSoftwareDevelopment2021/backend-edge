package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.ProcessDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;

public interface ProcessService {

    Response insertProcess(ProcessDTO processDTO);

    Response deleteProcess(String id);

    Response updateProcess(String id, ProcessDTO processDTO);

    Response getProcess(String id);

    Response getAllProcesses();

}
