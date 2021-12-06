package cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector.DataCollectorPO;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dyanjun
 * @date 2021/10/28 10:24
 */
@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ModbusCollector.class, name = "Modbus"),
        @JsonSubTypes.Type(value = HttpCollector.class, name = "Http"),
        @JsonSubTypes.Type(value = WebSocketCollector.class, name = "WebSocket"),
        @JsonSubTypes.Type(value = ZigBeeCollector.class, name = "ZigBee"),
})
public abstract class DataCollector {

    public abstract String execute(String id);

    protected abstract void verify();

    public abstract DataCollectorPO convertDomain2PO();

    public abstract Boolean monitor(String id);

    public abstract Boolean stopMonitor(String id);
}
