package cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector;

import static cn.edu.sjtu.ist.ecssbackendedge.model.sensor.function.ModbusFunction.COIL_STATUS;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.function.ModbusFunction;
import cn.edu.sjtu.ist.ecssbackendedge.utils.collect.ModbusUtil;

import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dyanjun
 * @date 2021/10/27 20:56
 */
@Slf4j
@Data
@NoArgsConstructor
public class ModbusCollector extends DataCollector {

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
    public String execute(String id) {
        try {
            String collectedData = modbusUtil.collectData(id, ip, port, slaveId, offset, num, COIL_STATUS, datatype);
            System.out.println(collectedData);
            System.out.println("---------------\n");
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

}
