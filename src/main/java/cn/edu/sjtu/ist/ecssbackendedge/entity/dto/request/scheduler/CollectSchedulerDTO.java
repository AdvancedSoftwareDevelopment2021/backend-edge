package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.scheduler;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.scheduler.CollectSchedulerPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.TimeUnit;
import lombok.Data;

import java.util.Date;

/**
 * @author dyanjun
 * @date 2021/10/31 19:47
 */
@Data
public class CollectSchedulerDTO {
    /**
     * 时间间隔
     */
    private int interval;

    /**
     * 时间单位
     */
    private TimeUnit unit;

    /**
     * 首次调度时间
     */
    private long startTime;

    public CollectSchedulerPO convert2PO() {
        CollectSchedulerPO res = new CollectSchedulerPO();
        res.setInterval(this.interval);
        res.setUnit(this.unit);
        res.setStartTime(new Date(startTime));
        return res;
    }

}
