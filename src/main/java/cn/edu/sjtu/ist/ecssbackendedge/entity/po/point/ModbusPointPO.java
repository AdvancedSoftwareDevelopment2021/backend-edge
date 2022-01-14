package cn.edu.sjtu.ist.ecssbackendedge.entity.po.point;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.ModbusPoint;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.function.ModbusFunction;

import lombok.Data;

/**
 * @author rsp
 * @version 0.1
 * @brief Modbus收集器PO类
 * @date 2021-11-21
 */
@Data
public class ModbusPointPO extends PointPO {

    private String ip;

    private Integer port;

    private Integer slaveId;

    private Integer offset;

    private ModbusFunction modbusFunction;

    @Override
    public ModbusPoint convertPO2Domain(PointPO pointPO) {
        ModbusPointPO collectorPO = (ModbusPointPO) pointPO;
        ModbusPoint collector = new ModbusPoint();

        collector.setIp(collectorPO.getIp());
        collector.setPort(collectorPO.getPort());
        collector.setModbusFunction(collectorPO.getModbusFunction());
        collector.setOffset(collectorPO.getOffset());
        collector.setSlaveId(collectorPO.getSlaveId());
        return collector;
    }
}
