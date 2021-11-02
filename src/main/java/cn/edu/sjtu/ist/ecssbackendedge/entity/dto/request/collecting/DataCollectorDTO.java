package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.collecting;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collecting.DataCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.collector.ModbusFunction;
import lombok.Data;
import org.springframework.beans.BeanUtils;

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

    public DataCollectorPO convert2PO() {
        DataCollectorPO res = new DataCollectorPO();
        BeanUtils.copyProperties(this, res);
        return res;
    }
}
