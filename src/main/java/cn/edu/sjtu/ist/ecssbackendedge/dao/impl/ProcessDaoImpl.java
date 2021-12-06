package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.ProcessDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.ProcessPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.process.Process;
import cn.edu.sjtu.ist.ecssbackendedge.repository.ProcessRepository;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.ProcessUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ProcessDaoImpl implements ProcessDao {

    @Autowired
    private ProcessUtil processUtil;

    @Autowired
    private ProcessRepository processRepository;

    @Override
    public Process createProcess(Process process) {
        ProcessPO po = processUtil.convertDomain2PO(process);
        processRepository.save(po);
        return processUtil.convertPO2Domain(po);
    }

    @Override
    public void removeProcess(String id) {
        processRepository.deleteProcessPOById(id);
    }

    @Override
    public void modifyProcess(Process process) {
        ProcessPO po = processRepository.findProcessById(process.getId());
        if (po != null) {
            po = processUtil.convertDomain2PO(process);
            processRepository.save(po);
        }
    }

    @Override
    public Process findProcessById(String id) {
        ProcessPO po = processRepository.findProcessById(id);
        Process process = processUtil.convertPO2Domain(po);
        log.info(String.valueOf(process));
        return process;
    }

    @Override
    public List<Process> findProcessByName(String name) {
        List<ProcessPO> processPOs = processRepository.findProcessPOSByName(name);
        List<Process> processs = new ArrayList<>();
        for (ProcessPO processPO : processPOs) {
            Process dm = processUtil.convertPO2Domain(processPO);
            processs.add(dm);
        }
        return processs;
    }

    @Override
    public List<Process> findAllProcesss() {
        List<ProcessPO> processPOs = processRepository.findAll();
        List<Process> processs = new ArrayList<>();
        for (ProcessPO processPO : processPOs) {
            Process dm = processUtil.convertPO2Domain(processPO);
            processs.add(dm);
        }
        return processs;
    }
}
