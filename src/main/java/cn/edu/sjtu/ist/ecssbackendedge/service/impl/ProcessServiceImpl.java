package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.ProcessDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.ProcessDTO;
import cn.edu.sjtu.ist.ecssbackendedge.model.process.Process;
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
    public ProcessDTO insertProcess(ProcessDTO processDTO) {
        Process process = processUtil.convertDTO2Domain(processDTO);
        return processUtil.convertDomain2DTO(processDao.createProcess(process));
    }

    @Override
    public void deleteProcess(String id) {
        processDao.removeProcess(id);
    }

    @Override
    public void updateProcess(String id, ProcessDTO processDTO) {
        processDTO.setId(id);
        Process process = processUtil.convertDTO2Domain(processDTO);
        processDao.modifyProcess(process);
    }

    @Override
    public ProcessDTO getProcess(String id) {
        Process process = processDao.findProcessById(id);
        ProcessDTO processDTO = processUtil.convertDomain2DTO(process);
        return processDTO;
    }

    @Override
    public List<ProcessDTO> getAllProcesses() {
        List<Process> processs = processDao.findAllProcesss();
        List<ProcessDTO> res = new ArrayList<>();
        for (Process process : processs) {
            ProcessDTO dto = processUtil.convertDomain2DTO(process);
            res.add(dto);
        }
        return res;
    }
}
