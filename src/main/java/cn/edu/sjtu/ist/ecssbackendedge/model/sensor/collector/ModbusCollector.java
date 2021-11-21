package cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector.ModbusCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.enumeration.MessageProtocol;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.function.ModbusFunction;
import cn.edu.sjtu.ist.ecssbackendedge.utils.collect.ModbusUtil;

import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
            return modbusUtil.collectData(id, ip, port, slaveId, offset, num, modbusFunction, datatype);
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

    @Override
    public Boolean monitor(String id) {
        log.error("modbus无法监听");
        return false;
    }

    @Override
    public Boolean stopMonitor(String id) {
        log.error("modbus无法停止监听");
        return false;
    }
}
