package cn.edu.sjtu.ist.ecssbackendedge.config;

import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.serotonin.modbus4j.ip.IpParameters;

import java.util.HashMap;

/**
 * @author dyanjun
 * @date 2021/10/27 20:49
 */
@Configuration
@Import(com.serotonin.modbus4j.ModbusFactory.class)
public class ModbusConfig {

    @Autowired
    private ModbusFactory modbusFactory;

    @Autowired
    @Qualifier("modbusMasterHashMap")
    private HashMap<String, ModbusMaster> masterMap;

    @Bean
    public HashMap<String, ModbusMaster> modbusMasterHashMap() {
        return new HashMap<>();
    }

    /**
     * @Title getMaster
     * @Description: 通过id获取对应的modbus连接器
     * @params: [id]
     * @return: com.serotonin.modbus4j.ModbusMaster
     */
    public ModbusMaster getMaster(String id, String ip, Integer port) {

        ModbusMaster modbusMaster = masterMap.get(id);
        if(modbusMaster == null) {
            setMaster(id, ip, port);
            modbusMaster = masterMap.get(id);
        }
        return modbusMaster;

    }

    /**
     * @Title setMaster
     * @Description: 设置ip对应的modbus连接器
     * @params: [id, ip, port]
     * @return: void
     */
    private void setMaster(String id, String ip, Integer port) {

        ModbusMaster master;
        IpParameters params = new IpParameters();
        params.setHost(ip);
        params.setPort(port);
        //设置为true，会导致TimeoutException: request=com.serotonin.modbus4j.ip.encap.EncapMessageRequest@774dfba5",
        //params.setEncapsulated(true);
        master = modbusFactory.createTcpMaster(params, false);// TCP 协议
        try {
            //设置超时时间
            master.setTimeout(3*1000);
            //设置重连次数
            master.setRetries(3);
            //初始化
            master.init();
        } catch (ModbusInitException e) {
            e.printStackTrace();
        }
        masterMap.put(id, master);
    }

}
