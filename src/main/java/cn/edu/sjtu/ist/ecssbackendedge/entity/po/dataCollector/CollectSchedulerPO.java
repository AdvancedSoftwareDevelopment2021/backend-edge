package cn.edu.sjtu.ist.ecssbackendedge.entity.po.dataCollector;

import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.TimeUnit;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author dyanjun
 * @date 2021/10/28 20:12
 */
@Data
@Entity
public class CollectSchedulerPO {
    private int interval;

    private TimeUnit unit;

    private Date startTime;
}
