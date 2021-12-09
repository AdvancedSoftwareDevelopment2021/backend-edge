package cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.collector.ModbusCollector;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.function.ModbusFunction;

import lombok.Data;

/**
 * @author rsp
 * @version 0.1
 * @brief Modbus收集器PO类
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
    public ModbusCollector convertPO2Domain(DataCollectorPO dataCollectorPO) {
        ModbusCollectorPO collectorPO = (ModbusCollectorPO) dataCollectorPO;
        ModbusCollector collector = new ModbusCollector();

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
