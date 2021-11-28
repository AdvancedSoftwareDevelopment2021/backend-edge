package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.scheduler.CollectSchedulerPO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.Date;

@Data
@Document(value = "edge")
public class EdgePO {

    @Id
    private String id;

    @Field
    private String cloudUrl;

    @Field
    private CollectSchedulerPO collectorScheduler;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

}
