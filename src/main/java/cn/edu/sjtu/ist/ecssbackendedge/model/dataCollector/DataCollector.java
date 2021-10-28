package cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector;

import cn.edu.sjtu.ist.ecssbackendedge.componect.DataCollectorFactory;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import org.quartz.core.QuartzScheduler;

import javax.persistence.MappedSuperclass;

/**
 * @author dyanjun
 * @date 2021/10/28 10:24
 */

@Data
public abstract class DataCollector {
    private String id;

    private String name;

    private CollectScheduler collectorScheduler;

    private Status status = Status.SLEEP;

    private QuartzScheduler quartzScheduler;

    private DataCollectorFactory factory;

    protected abstract void execute() throws Exception;

    protected abstract void verify();

    public void collectData(){

    }

    public void schedule(){

    }

    public void stopSchedule(){

    }
}
