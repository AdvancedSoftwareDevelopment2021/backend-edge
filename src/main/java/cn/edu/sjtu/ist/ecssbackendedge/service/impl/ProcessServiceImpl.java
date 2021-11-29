package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.ProcessDao;
import cn.edu.sjtu.ist.ecssbackendedge.model.process.Process;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.ProcessDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.service.ProcessService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.ProcessUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    private ProcessUtil processUtil;

    @Autowired
    private ProcessDao processDao;

    @Override
    public Response insertProcess(ProcessDTO processDTO) {
        Process process = processUtil.convertDTO2Domain(processDTO);
        processDao.createProcess(process);
        return new Response(200, "插入流程成功!", null);
    }

    @Override
    public Response deleteProcess(String id) {
        processDao.removeProcess(id);
        return new Response(200, "删除流程id=" + id + "成功!", null);
    }

    @Override
    public Response updateProcess(String id, ProcessDTO processDTO) {
        processDTO.setId(id);
        Process process = processUtil.convertDTO2Domain(processDTO);
        processDao.modifyProcess(process);
        return new Response(200, "更新流程id=" + id + "成功!", null);
    }

    @Override
    public Response getProcess(String id) {
        Process process = processDao.findProcessById(id);
        ProcessDTO processDTO = processUtil.convertDomain2DTO(process);
        return new Response(200, "获取流程id=" + id + "成功!", processDTO);
    }

    @Override
    public Response getAllProcesses() {
        List<Process> processs = processDao.findAllProcesss();
        List<ProcessDTO> res = new ArrayList<>();
        for (Process process: processs) {
            ProcessDTO dto = processUtil.convertDomain2DTO(process);
            res.add(dto);
        }
        return new Response(200, "获取所有流程成功", res);
    }
}
