package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.Param;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.point.ModbusPointPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.MessageProtocol;
import cn.edu.sjtu.ist.ecssbackendedge.utils.point.ModbusUtil;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.function.ModbusFunction;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author dyanjun
 * @date 2021/10/27 20:56
 */

@Slf4j
@Data
@NoArgsConstructor
public class ModbusPoint extends Point {

    private String ip;

    private Integer port;

    private Integer slaveId;

    private Integer offset;

    private ModbusFunction modbusFunction;

    private ModbusUtil modbusUtil;

    @Override
    public String execute(String id) {
        try {
            return modbusUtil.collectData(id, ip, port, slaveId, offset, modbusFunction);
        } catch (ModbusTransportException | ErrorResponseException e) {
            log.warn("收集数据出错，error: " + e.getMessage());
            return null;
        }
    }

    @Override
    protected void verify() {
        if (ip == null) {
            throw new RuntimeException("ip不能为空");
        }
        if (port == null) {
            throw new RuntimeException("port不能为空");
        }
    }

    @Override
    public ModbusPointPO convertDomain2PO() {
        ModbusPointPO collectorPO = new ModbusPointPO();

        collectorPO.setType(MessageProtocol.MODBUS.getProtocol());
        collectorPO.setIp(ip);
        collectorPO.setPort(port);
        collectorPO.setModbusFunction(modbusFunction);
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

    @Override
    public Boolean executeCustomCommand(String id, List<Param> paramss) {
        log.error("modbus无法传递自定义参数");
        return false;
    }

    @Override
    public Boolean executePropertyCommand(String id, String type, String value) throws ErrorResponseException, ModbusTransportException {
        modbusUtil.writeValue(id + "_driver", ip, port, slaveId, offset, modbusFunction, type, value);
        return null;
    }

    @Override
    public File executeMLCommand(String id) {
        return null;
    }
}
