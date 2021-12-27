package cn.edu.sjtu.ist.ecssbackendedge.utils.point.zigbee;

import cn.edu.sjtu.ist.ecssbackendedge.config.ZigBeeConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dyanjun
 * @date 2021/11/21 0:58
 */
@Component
@Slf4j
public class ZigBeeUtil {

    @Autowired
    ZigBeeConfig zigBeeConfig;

    public void startMonitor(String id, String serialNumber, int baudRate, int checkoutBit, int dataBit, int stopBit) {
        ZigBeeListener client = zigBeeConfig.getListener(id, serialNumber, baudRate, checkoutBit, dataBit, stopBit);
    }

    public void stopMonitor(String id) {
        zigBeeConfig.deleteListener(id);
    }

    public void SendMss(String id, String value, String serialNumber, int baudRate, int checkoutBit, int dataBit, int stopBit){
        ZigBeeListener client = zigBeeConfig.getListener(id, serialNumber, baudRate, checkoutBit, dataBit, stopBit);
        client.sendComm(value);
    }
}
