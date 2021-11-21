package cn.edu.sjtu.ist.ecssbackendedge.utils.convert;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.scheduler.CollectSchedulerPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @brief CollectScheduler对象转换工具类
 * @author rsp
 * @version 0.1
 * @date 2021-11-20
 */
@Component
public class CollectSchedulerUtil {

    public CollectSchedulerPO convertDomain2PO(CollectScheduler scheduler) {
        if(scheduler==null) return null;
        CollectSchedulerPO res = new CollectSchedulerPO();
        res.setInterval(scheduler.getInterval());
        res.setUnit(scheduler.getUnit());
        res.setStartTime(scheduler.getStartTime() == null ? new Date() : scheduler.getStartTime());
        return res;
    }

    public CollectScheduler convertPO2Domain(CollectSchedulerPO schedulerPO) {
        if(schedulerPO==null) return null;
        CollectScheduler res = new CollectScheduler();
        res.setInterval(schedulerPO.getInterval());
        res.setUnit(schedulerPO.getUnit());
        res.setStartTime(schedulerPO.getStartTime());
        return res;
    }
}
