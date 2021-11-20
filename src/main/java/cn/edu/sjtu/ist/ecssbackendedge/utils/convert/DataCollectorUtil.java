package cn.edu.sjtu.ist.ecssbackendedge.utils.convert;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector.DataCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector.HttpCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector.ModbusCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector.WebSocketCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.DataCollector;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.HttpCollector;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.ModbusCollector;
import cn.edu.sjtu.ist.ecssbackendedge.model.enumeration.MessageProtocol;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.WebSocketCollector;
import cn.edu.sjtu.ist.ecssbackendedge.utils.collect.ModbusUtil;
import cn.edu.sjtu.ist.ecssbackendedge.utils.collect.websocket.WebSocketUtil;
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

    public DataCollector convertPO2Domain(DataCollectorPO dataCollectorPO) {
        DataCollector dataCollector = null;
        MessageProtocol protocol = MessageProtocol.fromString(dataCollectorPO.getType());
        switch (Objects.requireNonNull(protocol)) {
            case MODBUS:
                ModbusCollector modbusCollector = (new ModbusCollectorPO()).convertPO2Domain(dataCollectorPO);
                modbusCollector.setModbusUtil(modbusUtil);
                return modbusCollector;
            case ZIGBEE:
                break;
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
