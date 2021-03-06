package cn.edu.sjtu.ist.ecssbackendedge.entity.po.sensor;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.point.PointPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.scheduler.CollectSchedulerPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.Status;

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
@Document("Sensor")
public class SensorPO {
    @Id
    private String id;

    private String deviceId;

    private String name;

    private CollectSchedulerPO collectorScheduler;

    private Status status;

    private PointPO pointPO;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

}
