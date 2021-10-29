package cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.dataCollector.DataCollectorDTO;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.modbus.ModbusCollector;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import org.quartz.core.QuartzScheduler;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * @author dyanjun
 * @date 2021/10/28 10:24
 */
@Data
@NoArgsConstructor
@Document(collection = "dataCollector")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ModbusCollector.class, name = "Modbus")
})
@Slf4j
public abstract class DataCollector {
    @Id
    protected String id;

    protected String name;

    protected CollectScheduler collectorScheduler;

    protected Status status = Status.SLEEP;

    protected QuartzScheduler quartzScheduler;

    abstract public DataCollectorDTO convert2DTO();

    protected abstract void execute() throws Exception;

    protected abstract void verify();

    public void collectData(){

    }

    public void schedule(){

    }

    public void stopSchedule(){

    }
}
