package cn.edu.sjtu.ist.ecssbackendedge.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProcessDTO {

    private String id;

    private String name;

    private String bpmn;

    private Date createdTime;

}
