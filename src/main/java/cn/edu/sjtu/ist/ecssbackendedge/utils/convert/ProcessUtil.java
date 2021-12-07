package cn.edu.sjtu.ist.ecssbackendedge.utils.convert;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.process.ProcessDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.ProcessPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.Process;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class ProcessUtil {

    public Process convertDTO2Domain(ProcessDTO dto) {
        Process res = new Process();
        res.setId(dto.getId());
        res.setName(dto.getName());
        res.setBpmn(dto.getBpmn());
        res.setCreatedTime(dto.getCreatedTime() == null ? new Date() : dto.getCreatedTime());
        res.setStatus(dto.getStatus());
        res.setStep(dto.getStep());
        res.setDeviceList(dto.getDeviceList() == null ? new ArrayList<>() : dto.getDeviceList());
        return res;
    }

    public ProcessDTO convertDomain2DTO(Process domain) {
        ProcessDTO res = new ProcessDTO();
        res.setId(domain.getId());
        res.setName(domain.getName());
        res.setBpmn(domain.getBpmn());
        res.setCreatedTime(domain.getCreatedTime() == null ? new Date() : domain.getCreatedTime());
        res.setStatus(domain.getStatus());
        res.setStep(domain.getStep());
        res.setDeviceList(domain.getDeviceList());
        return res;
    }

    public Process convertPO2Domain(ProcessPO po) {
        Process res = new Process();
        res.setId(po.getId());
        res.setName(po.getName());
        res.setBpmn(po.getBpmn());
        res.setCreatedTime(po.getCreatedTime() == null ? new Date() : po.getCreatedTime());
        res.setStatus(po.getStatus());
        res.setStep(po.getStep());
        res.setDeviceList(po.getDeviceList() == null ? new ArrayList<>() : po.getDeviceList());
        return res;
    }

    public ProcessPO convertDomain2PO(Process domain) {
        ProcessPO res = new ProcessPO();
        res.setId(domain.getId());
        res.setName(domain.getName());
        res.setBpmn(domain.getBpmn());
        res.setCreatedTime(domain.getCreatedTime() == null ? new Date() : domain.getCreatedTime());
        res.setStatus(domain.getStatus());
        res.setStep(domain.getStep());
        res.setDeviceList(domain.getDeviceList() == null ? new ArrayList<>() : domain.getDeviceList());
        return res;
    }
}
