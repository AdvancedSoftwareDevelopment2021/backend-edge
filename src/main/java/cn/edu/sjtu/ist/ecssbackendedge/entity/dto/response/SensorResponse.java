package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.response;

import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.model.enumeration.Status;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.DataCollector;
import lombok.Data;

/**
 * @brief Sensor的返回DTO
 * @author rsp
 * @version 0.1
 * @date 2021-11-20
 */
@Data
public class SensorResponse {

    private String id;

    private String deviceId;

    private String name;

    private Status status;

    private CollectScheduler collectorScheduler;

    private DataCollector dataCollector;

}
