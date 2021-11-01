package cn.edu.sjtu.ist.ecssbackendedge.entity.po.collecting;


import cn.edu.sjtu.ist.ecssbackendedge.entity.po.scheduler.CollectSchedulerPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.DataCollecting;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author dyanjun
 * @date 2021/10/31 18:47
 */
@Data
@Document("DataCollecting")
public class DataCollectingPO {
    @Id
    private String id;

    private String name;

    private CollectSchedulerPO collectorScheduler;

    private Status status;

    private DataCollectorPO dataCollector;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    public void syncStatusWith(DataCollecting dataCollecting) {
        this.status = dataCollecting.getStatus();
    }
}
