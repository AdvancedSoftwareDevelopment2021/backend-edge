package cn.edu.sjtu.ist.ecssbackendedge.entity.po.point;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.Point;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/10/31 18:52
 */
@Data
public abstract class PointPO {

    private String type;

    public abstract Point convertPO2Domain(PointPO collectorPO);

}
