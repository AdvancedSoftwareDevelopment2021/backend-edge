package cn.edu.sjtu.ist.ecssbackendedge.entity.po.dataCollector;

import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.Status;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import com.serotonin.modbus4j.code.DataType;
import lombok.Data;
import org.quartz.core.QuartzScheduler;

import javax.persistence.Entity;

/**
 * @author dyanjun
 * @date 2021/10/28 11:51
 */
@Data
@Entity
public class DataCollectorPO {
    private String id;

    private String name;

    private CollectSchedulerPO collectorSchedulerPO;

    private Status status;

   // MODBUS
   private String ip;

    private Integer port;

    private Integer slaveId;

    private Integer offset;

    private DataType datatype; //TODO

    private Integer num;

}
