package cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.collector.DataCollector;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/10/31 18:52
 */
@Data
public abstract class DataCollectorPO {

    private String type;

    public abstract DataCollector convertPO2Domain(DataCollectorPO collectorPO);

}
