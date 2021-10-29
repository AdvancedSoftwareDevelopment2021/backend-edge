package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.dataCollector;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.dataCollector.DataCollectorDTO;
import com.serotonin.modbus4j.code.DataType;

import java.io.Serializable;

/**
 * @author dyanjun
 * @date 2021/10/29 10:15
 */
public class ModbusCollectorDTO extends DataCollectorDTO implements Serializable {
    private String id;
    private String name;

    private String ip;
    private Integer port;

    private Integer slaveId;

    private Integer offset;

    private String datatype; //TODO

    private Integer num;
}
