package cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector.ZigBeeCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.enumeration.MessageProtocol;
import cn.edu.sjtu.ist.ecssbackendedge.utils.collect.zigbee.ZigBeeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dyanjun
 * @date 2021/11/21 1:05
 */
@Slf4j
@Data
@NoArgsConstructor
public class ZigBeeCollector extends DataCollector {

    private String serialNumber;// 串口号
    private int baudRate;        // 波特率
    private int checkoutBit;    // 校验位
    private int dataBit;        // 数据位
    private int stopBit;        // 停止位

    private ZigBeeUtil zigBeeUtil;

    @Override
    public String execute(String id) {
        return null;
    }

    @Override
    protected void verify() {

    }

    @Override
    public ZigBeeCollectorPO convertDomain2PO() {
        ZigBeeCollectorPO collectorPO = new ZigBeeCollectorPO();

        collectorPO.setType(MessageProtocol.ZIGBEE.getProtocol());
        collectorPO.setSerialNumber(serialNumber);
        collectorPO.setBaudRate(baudRate);
        collectorPO.setCheckoutBit(checkoutBit);
        collectorPO.setDataBit(dataBit);
        collectorPO.setStopBit(stopBit);

        return collectorPO;
    }

    @Override
    public Boolean monitor(String id) {
        zigBeeUtil.startMonitor(id, serialNumber, baudRate, checkoutBit, dataBit, stopBit);
        return true;
    }

    @Override
    public Boolean stopMonitor(String id) {
        zigBeeUtil.stopMonitor(id);
        return true;
    }
}
