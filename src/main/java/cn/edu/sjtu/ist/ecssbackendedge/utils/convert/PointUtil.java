package cn.edu.sjtu.ist.ecssbackendedge.utils.convert;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.point.*;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.MessageProtocol;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.*;
import cn.edu.sjtu.ist.ecssbackendedge.utils.point.ModbusUtil;
import cn.edu.sjtu.ist.ecssbackendedge.utils.point.websocket.WebSocketUtil;
import cn.edu.sjtu.ist.ecssbackendedge.utils.point.zigbee.ZigBeeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author rsp
 * @version 0.1
 * @brief DataCollector对象转换工具类
 * @date 2021-11-20
 */
@Component
public class PointUtil {

    @Autowired
    private ModbusUtil modbusUtil;

    @Autowired
    private WebSocketUtil webSocketUtil;

    @Autowired
    private ZigBeeUtil zigBeeUtil;

    public Point convertPO2Domain(PointPO pointPO) {
        Point point = null;
        MessageProtocol protocol = MessageProtocol.fromString(pointPO.getType());
        switch (Objects.requireNonNull(protocol)) {
            case MODBUS:
                ModbusPoint modbusPoint = (new ModbusPointPO()).convertPO2Domain(pointPO);
                modbusPoint.setModbusUtil(modbusUtil);
                return modbusPoint;
            case ZIGBEE:
                ZigBeePoint zigBeePoint = (new ZigBeePointPO()).convertPO2Domain(pointPO);
                zigBeePoint.setZigBeeUtil(zigBeeUtil);
                return zigBeePoint;
            case CANBUS:
                break;
            case HTTP:
                HttpPoint httpPoint = (new HttpPointPO()).convertPO2Domain(pointPO);
                return httpPoint;
            case WEBSOCKET:
                WebSocketPoint webSocketPoint = (new WebSocketPointPO()).convertPO2Domain(pointPO);
                webSocketPoint.setWebSocketUtil(webSocketUtil);
                return webSocketPoint;
            default:
                break;
        }
        return point;
    }

    public PointPO convertDomain2PO(Point point) {
        return point.convertDomain2PO();
    }

}
