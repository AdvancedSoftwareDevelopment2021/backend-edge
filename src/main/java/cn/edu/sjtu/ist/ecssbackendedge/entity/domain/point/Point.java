package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.point.PointPO;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author dyanjun
 * @date 2021/10/28 10:24
 */
@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ModbusPoint.class, name = "Modbus"),
        @JsonSubTypes.Type(value = HttpPoint.class, name = "Http"),
        @JsonSubTypes.Type(value = WebSocketPoint.class, name = "WebSocket"),
        @JsonSubTypes.Type(value = ZigBeePoint.class, name = "ZigBee"),
})
public abstract class Point {

    public abstract String execute(String id);

    protected abstract void verify();

    public abstract PointPO convertDomain2PO();

    public abstract Boolean monitor(String id);

    public abstract Boolean stopMonitor(String id);

    public abstract Boolean executeCustomCommand(String id, Map<String, Object> params);

    public abstract Boolean executePropertyCommand(String id, String type, String value) throws ErrorResponseException, ModbusTransportException;
}
