package cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector;

import static cn.edu.sjtu.ist.ecssbackendedge.model.sensor.function.ModbusFunction.COIL_STATUS;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector.ModbusCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.function.ModbusFunction;
import cn.edu.sjtu.ist.ecssbackendedge.utils.MessageProtocol;
import cn.edu.sjtu.ist.ecssbackendedge.utils.collect.ModbusUtil;

import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author dyanjun
 * @date 2021/10/27 20:56
 */

@Slf4j
@Data
@NoArgsConstructor
public class ModbusCollector extends DataCollector {

    private String ip;

    private Integer port;

    private Integer slaveId;

    private Integer offset;

    private Integer num;

    private ModbusFunction modbusFunction;

    private String datatype; //TODO

    private ModbusUtil modbusUtil;

    @Override
    public String execute(String id) {
        try {
            String collectedData = modbusUtil.collectData(id, ip, port, slaveId, offset, num, modbusFunction, datatype);
            System.out.println(collectedData);
            return collectedData;
        } catch (ModbusTransportException e) {
            log.warn("收集数据出错，error: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void verify() {
        if(ip == null){
            throw new RuntimeException("ip不能为空");
        }
        if(port == null){
            throw new RuntimeException("port不能为空");
        }
    }

    @Override
    public ModbusCollectorPO convertDomain2PO() {
        ModbusCollectorPO collectorPO = new ModbusCollectorPO();

        collectorPO.setType(MessageProtocol.MODBUS.getProtocol());
        collectorPO.setIp(ip);
        collectorPO.setPort(port);
        collectorPO.setModbusFunction(modbusFunction);
        collectorPO.setDatatype(datatype);
        collectorPO.setNum(num);
        collectorPO.setOffset(offset);
        collectorPO.setSlaveId(slaveId);
        return collectorPO;
    }
}
