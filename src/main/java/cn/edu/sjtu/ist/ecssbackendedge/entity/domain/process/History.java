package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document("history")
public class History {
    private String id;

    private String process;

    private String bpmnInstance;

    private int status;

    private Date start;

    private Date end;

}
