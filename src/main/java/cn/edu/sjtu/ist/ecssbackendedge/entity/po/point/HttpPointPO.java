package cn.edu.sjtu.ist.ecssbackendedge.entity.po.point;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.HttpPoint;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/11/19 17:55
 */
@Data
public class HttpPointPO extends PointPO {

    private String url;

    @Override
    public HttpPoint convertPO2Domain(PointPO pointPO) {
        HttpPointPO collectorPO = (HttpPointPO) pointPO;
        HttpPoint collector = new HttpPoint();
        collector.setUrl(collectorPO.getUrl());
        return collector;
    }
}
