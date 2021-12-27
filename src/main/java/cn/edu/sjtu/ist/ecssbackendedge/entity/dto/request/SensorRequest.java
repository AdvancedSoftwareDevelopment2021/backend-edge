package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.Point;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/10/31 19:44
 */
@Data
public class SensorRequest {

    private String name;

    /**
     * 数据采集器
     */
    private Point point;

    /**
     * 调度信息
     */
    private CollectScheduler collectScheduler;
}
