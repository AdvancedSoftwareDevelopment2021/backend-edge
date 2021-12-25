package cn.edu.sjtu.ist.ecssbackendedge.entity.po.point;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.ZigBeePoint;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/11/21 17:35
 */
@Data
public class ZigBeePointPO extends PointPO {

    private String serialNumber;
    private int baudRate;
    private int checkoutBit;
    private int dataBit;
    private int stopBit;

    @Override
    public ZigBeePoint convertPO2Domain(PointPO pointPO) {
        ZigBeePointPO collectorPO = (ZigBeePointPO) pointPO;
        ZigBeePoint collector = new ZigBeePoint();

        collector.setSerialNumber(collectorPO.getSerialNumber());
        collector.setBaudRate(collectorPO.getBaudRate());
        collector.setCheckoutBit(collectorPO.getCheckoutBit());
        collector.setDataBit(collectorPO.getDataBit());
        collector.setStopBit(collectorPO.getStopBit());

        return collector;
    }
}
