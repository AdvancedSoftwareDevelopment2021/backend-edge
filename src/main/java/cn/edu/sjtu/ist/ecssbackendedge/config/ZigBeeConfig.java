package cn.edu.sjtu.ist.ecssbackendedge.config;

import cn.edu.sjtu.ist.ecssbackendedge.dao.SensorDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.Sensor;
import cn.edu.sjtu.ist.ecssbackendedge.utils.point.zigbee.ZigBeeListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dyanjun
 * @date 2021/11/10 23:55
 */
@Configuration
@Slf4j
public class ZigBeeConfig {
    @Autowired
    @Qualifier("zigBeeListenerHashMap")
    private ConcurrentHashMap<String, ZigBeeListener> listenerMap;

    @Bean
    public ConcurrentHashMap<String, ZigBeeListener> zigBeeListenerHashMap() {
        return new ConcurrentHashMap<>();
    }

    @Autowired
    private SensorDao sensorDao;


    /**
     * @Title getListener
     * @Description: 通过id获取对应的ZigBeeListener
     * @params: [id, serialNumber, baudRate, checkoutBit, dataBit, stopBit]
     * @return: ZigBeeListener
     */
    public ZigBeeListener getListener(String id, String serialNumber, int baudRate, int checkoutBit, int dataBit, int stopBit) {
        ZigBeeListener listener = listenerMap.get(id);
        if (listener == null) {
            setListener(id, serialNumber, baudRate, checkoutBit, dataBit, stopBit);
            listener = listenerMap.get(id);
        }

        return listener;

    }

    /**
     * @return void
     * @Title setListener
     * @Description: 设置id对应的ZigBeeListener
     * @params [id, serialNumber, baudRate, checkoutBit, dataBit, stopBit]
     */
    private void setListener(String id, String serialNumber, int baudRate, int checkoutBit, int dataBit, int stopBit) {
        Sensor sensor = null;
        ZigBeeListener listener = null;
        try{
            sensor = sensorDao.findSensorById(id);
            listener = new ZigBeeListener(sensor);
        }catch (Exception e){
            listener = new ZigBeeListener();
        }
        listener.init(serialNumber, baudRate, checkoutBit, dataBit, stopBit);
        listenerMap.put(id, listener);
    }

    public void deleteListener(String id) {
        ZigBeeListener listener = listenerMap.get(id);
        if (listener == null) {
            return;
        }
        listener.closeSerialPort();
        listenerMap.remove(id);
    }

    private void sendMessage(String id,String value){
        ZigBeeListener listener = listenerMap.get(id);
        if (listener == null) {
            return;
        }
        listener.sendComm(value);
    }
}
