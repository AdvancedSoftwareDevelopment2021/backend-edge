package cn.edu.sjtu.ist.ecssbackendedge.model.process;

import lombok.Data;

import java.util.Date;

@Data
public class Process {

    private String id;

    private String name;

    private String bpmn;

    private Date createdTime;

}
