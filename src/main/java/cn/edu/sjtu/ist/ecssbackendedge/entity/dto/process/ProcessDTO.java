package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.process;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.Status;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.Step;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ProcessDTO {

    private String id;

    private String name;

    private String bpmn;

    private int interval;

    private Date createdTime;

    private Step step;

    private Status status;

    private List<String> DeviceList;

}
