package cn.edu.sjtu.ist.ecssbackendedge.utils.convert;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector.*;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.*;
import cn.edu.sjtu.ist.ecssbackendedge.model.enumeration.MessageProtocol;
import cn.edu.sjtu.ist.ecssbackendedge.utils.collect.ModbusUtil;
import cn.edu.sjtu.ist.ecssbackendedge.utils.collect.websocket.WebSocketUtil;
import cn.edu.sjtu.ist.ecssbackendedge.utils.collect.zigbee.ZigBeeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @brief DataCollector对象转换工具类
 * @author rsp
 * @version 0.1
 * @date 2021-11-20
 */
@Component
public class DataCollectorUtil {

    @Autowired
    private ModbusUtil modbusUtil;

    @Autowired
    private WebSocketUtil webSocketUtil;

    @Autowired
    private ZigBeeUtil zigBeeUtil;

    public DataCollector convertPO2Domain(DataCollectorPO dataCollectorPO) {
        DataCollector dataCollector = null;
        MessageProtocol protocol = MessageProtocol.fromString(dataCollectorPO.getType());
        switch (Objects.requireNonNull(protocol)) {
            case MODBUS:
                ModbusCollector modbusCollector = (new ModbusCollectorPO()).convertPO2Domain(dataCollectorPO);
                modbusCollector.setModbusUtil(modbusUtil);
                return modbusCollector;
            case ZIGBEE:
                ZigBeeCollector zigBeeCollector = (new ZigBeeCollectorPO()).convertPO2Domain(dataCollectorPO);
                zigBeeCollector.setZigBeeUtil(zigBeeUtil);
                return zigBeeCollector;
            case CANBUS:
                break;
            case HTTP:
                HttpCollector httpCollector = (new HttpCollectorPO()).convertPO2Domain(dataCollectorPO);
                return httpCollector;
            case WEBSOCKET:
                WebSocketCollector webSocketCollector = (new WebSocketCollectorPO()).convertPO2Domain(dataCollectorPO);
                webSocketCollector.setWebSocketUtil(webSocketUtil);
                return webSocketCollector;
            default:
                break;
        }
        return dataCollector;
    }

    public DataCollectorPO convertDomain2PO(DataCollector dataCollector) {
        return dataCollector.convertDomain2PO();
    }

}
