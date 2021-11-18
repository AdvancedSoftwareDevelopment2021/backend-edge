package cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector;

import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.DataCollector;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.ModbusCollector;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.function.ModbusFunction;

import lombok.Data;

/**
 * @brief Modbus收集器PO类
 * @author rsp
 * @version 0.1
 * @date 2021-11-21
 */
@Data
public class ModbusCollectorPO extends DataCollectorPO {

    private String ip;

    private Integer port;

    private Integer slaveId;

    private Integer offset;

    private Integer num;

    private ModbusFunction modbusFunction;

    private String datatype; //TODO

    @Override
    public ModbusCollectorPO convertDomain2PO(DataCollector dataCollector) {
        ModbusCollector collector = (ModbusCollector)dataCollector;
        ModbusCollectorPO collectorPO = new ModbusCollectorPO();

        collectorPO.setProtocol(collector.getProtocol());
        collectorPO.setIp(collector.getIp());
        collectorPO.setPort(collector.getPort());
        collectorPO.setModbusFunction(collector.getModbusFunction());
        collectorPO.setDatatype(collector.getDatatype());
        collectorPO.setNum(collector.getNum());
        collectorPO.setOffset(collector.getOffset());
        collectorPO.setSlaveId(collector.getSlaveId());
        return collectorPO;
    }

    @Override
    public ModbusCollector convertPO2Domain(DataCollectorPO dataCollectorPO) {
        ModbusCollectorPO collectorPO = (ModbusCollectorPO)dataCollectorPO;
        ModbusCollector collector = new ModbusCollector();

        collector.setProtocol(collectorPO.getProtocol());
        collector.setIp(collectorPO.getIp());
        collector.setPort(collectorPO.getPort());
        collector.setModbusFunction(collectorPO.getModbusFunction());
        collector.setDatatype(collectorPO.getDatatype());
        collector.setNum(collectorPO.getNum());
        collector.setOffset(collectorPO.getOffset());
        collector.setSlaveId(collectorPO.getSlaveId());
        return collector;
    }
}
