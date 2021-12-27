package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.response;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.Status;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.Point;

import lombok.Data;

/**
 * @author rsp
 * @version 0.1
 * @brief Sensor的返回DTO
 * @date 2021-11-20
 */
@Data
public class SensorResponse {

    private String id;

    private String deviceId;

    private String name;

    private Status status;

    private CollectScheduler collectorScheduler;

    private Point point;

}
