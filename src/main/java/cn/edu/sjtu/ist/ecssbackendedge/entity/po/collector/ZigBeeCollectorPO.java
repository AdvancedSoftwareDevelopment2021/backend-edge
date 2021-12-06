package cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector;

import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.ZigBeeCollector;
import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/11/21 17:35
 */
@Data
public class ZigBeeCollectorPO extends DataCollectorPO {

    private String serialNumber;
    private int baudRate;
    private int checkoutBit;
    private int dataBit;
    private int stopBit;

    @Override
    public ZigBeeCollector convertPO2Domain(DataCollectorPO dataCollectorPO) {
        ZigBeeCollectorPO collectorPO = (ZigBeeCollectorPO) dataCollectorPO;
        ZigBeeCollector collector = new ZigBeeCollector();

        collector.setSerialNumber(collectorPO.getSerialNumber());
        collector.setBaudRate(collectorPO.getBaudRate());
        collector.setCheckoutBit(collectorPO.getCheckoutBit());
        collector.setDataBit(collectorPO.getDataBit());
        collector.setStopBit(collectorPO.getStopBit());

        return collector;
    }
}
