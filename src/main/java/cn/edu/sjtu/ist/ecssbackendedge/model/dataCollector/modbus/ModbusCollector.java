package cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.modbus;

import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.DataCollector;
import cn.edu.sjtu.ist.ecssbackendedge.utils.ModbusUtil;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.serotonin.modbus4j.code.DataType;

import javax.annotation.Resource;

/**
 * @author dyanjun
 * @date 2021/10/27 20:56
 */
@Data
@Slf4j
public class ModbusCollector extends DataCollector{

    private String ip;

    private Integer port;

    private Integer slaveId;

    private Integer offset;

    private DataType datatype; //TODO

    private Integer num;
    @Resource
    private ModbusUtil modbusUtil;

    @Override
    protected void execute() throws ModbusTransportException {
        modbusUtil.readCoilStatus(ip, offset, num);
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


}
