package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

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

}
