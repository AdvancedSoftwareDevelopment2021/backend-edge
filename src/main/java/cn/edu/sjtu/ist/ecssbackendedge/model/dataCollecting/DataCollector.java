package cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting;

import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.collector.ModbusCollector;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dyanjun
 * @date 2021/10/28 10:24
 */
@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ModbusCollector.class, name = "Modbus")
})
@Slf4j
public abstract class DataCollector {
    protected DataCollecting dataCollecting;

    protected abstract boolean execute(String id) throws Exception;

    protected abstract void verify();
}
