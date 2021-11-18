package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.collecting;

import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.function.ModbusFunction;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dyanjun
 * @date 2021/10/29 10:30
 */
@Data
public class DataCollectorDTO implements Serializable {
    String type;

    String ip;

    Integer port;

    Integer slaveId;

    Integer offset;

    ModbusFunction modbusFunction;

    String datatype;

    Integer num;

}
