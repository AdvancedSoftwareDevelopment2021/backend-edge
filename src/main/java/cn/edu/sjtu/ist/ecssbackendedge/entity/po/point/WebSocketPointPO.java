package cn.edu.sjtu.ist.ecssbackendedge.entity.po.point;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.WebSocketPoint;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/11/20 20:15
 */
@Data
public class WebSocketPointPO extends PointPO {

    private String uri;

    @Override
    public WebSocketPoint convertPO2Domain(PointPO pointPO) {
        WebSocketPointPO collectorPO = (WebSocketPointPO) pointPO;
        WebSocketPoint collector = new WebSocketPoint();

        collector.setUri(collectorPO.getUri());

        return collector;
    }
}
