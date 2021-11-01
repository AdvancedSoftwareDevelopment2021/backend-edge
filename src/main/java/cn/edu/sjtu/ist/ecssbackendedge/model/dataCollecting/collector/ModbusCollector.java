package cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.collector;

import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.DataCollector;
import cn.edu.sjtu.ist.ecssbackendedge.utils.ModbusUtil;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.NonNull;

/**
 * @author dyanjun
 * @date 2021/10/27 20:56
 */
@Data
@NoArgsConstructor
public class ModbusCollector extends DataCollector{

    @NonNull
    private String ip;

    @NonNull
    private Integer port;

    private Integer slaveId;

    @NonNull
    private Integer offset;

    @NonNull
    private Integer num;

    private ModbusFunction modbusFunction;

    @NonNull
    private String datatype; //TODO

    private ModbusUtil modbusUtil;


    @Override
    protected boolean execute(String id) throws ModbusTransportException {
        modbusUtil.collectData(id, ip, port, slaveId, offset, num, modbusFunction, datatype);
        return true;
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
