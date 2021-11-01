package cn.edu.sjtu.ist.ecssbackendedge.entity.po.collecting;

import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.collector.ModbusFunction;
import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/10/31 18:52
 */
@Data
public class DataCollectorPO {
    final static public String ModbusType = "Modbus";

    private String type;

    private String ip;

    private Integer port;

    private Integer slaveId;

    private Integer offset;

    private ModbusFunction modbusFunction;

    private String datatype;

    private Integer num;
}
