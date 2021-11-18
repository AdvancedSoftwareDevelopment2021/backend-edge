package cn.edu.sjtu.ist.ecssbackendedge.utils.convert;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector.DataCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector.ModbusCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.DataCollector;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.ModbusCollector;
import cn.edu.sjtu.ist.ecssbackendedge.utils.MessageProtocol;
import cn.edu.sjtu.ist.ecssbackendedge.utils.collect.ModbusUtil;
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

    public DataCollector convertPO2Domain(DataCollectorPO dataCollectorPO) {
        DataCollector dataCollector = null;
        MessageProtocol protocol = MessageProtocol.fromString(dataCollectorPO.getProtocol());
        switch (Objects.requireNonNull(protocol)) {
            case MODBUS:
                ModbusCollector collector = (new ModbusCollectorPO()).convertPO2Domain(dataCollectorPO);
                collector.setModbusUtil(modbusUtil);
                return collector;
            case ZIGBEE:
            case CANBUS:
            case HTTP:
                break;
            default:
                break;
        }
        return dataCollector;
    }

    public DataCollectorPO convertDomain2PO(DataCollector dataCollector) {
        DataCollectorPO dataCollectorPO = null;
        MessageProtocol protocol = MessageProtocol.fromString(dataCollector.getProtocol());
        switch (Objects.requireNonNull(protocol)) {
            case MODBUS:
                ModbusCollectorPO collectorPO = new ModbusCollectorPO();
                dataCollectorPO = collectorPO.convertDomain2PO(dataCollector);
            case ZIGBEE:
            case CANBUS:
            case HTTP:
                break;
            default:
                break;
        }
        return dataCollectorPO;
    }

}
