package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.collecting;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.scheduler.CollectSchedulerDTO;
import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/10/31 19:44
 */
@Data
public class AddDataCollectingRequest {
    /**
     * 数据采集器
     */
    private DataCollectorDTO dataCollector;

    private String name;

    /**
     * 调度信息
     */
    private CollectSchedulerDTO collectScheduler;
}
