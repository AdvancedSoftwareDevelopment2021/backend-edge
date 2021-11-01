package cn.edu.sjtu.ist.ecssbackendedge.entity.po.scheduler;

import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.TimeUnit;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author dyanjun
 * @date 2021/10/31 18:54
 */
@Data
public class CollectSchedulerPO {
    private int interval;

    private TimeUnit unit;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    public CollectScheduler convert2Domain() {
        CollectScheduler res = new CollectScheduler();
        BeanUtils.copyProperties(this, res);
        return res;
    }
}