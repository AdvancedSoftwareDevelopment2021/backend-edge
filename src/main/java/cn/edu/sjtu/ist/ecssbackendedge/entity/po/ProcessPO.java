package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.Status;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.Step;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document("process")
public class ProcessPO {

    @Id
    private String id;

    @Field
    private String name;

    @Field
    private String bpmn;

    @Field
    private Date createdTime;

    @Field
    private Step step = Step.BPMN;

    @Field
    private Status status = Status.CONSTRUCTING;

    @Field
    private List<String> DeviceList = new ArrayList<>();
}
