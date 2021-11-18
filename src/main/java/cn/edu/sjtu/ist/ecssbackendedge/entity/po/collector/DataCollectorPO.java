package cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector;

import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.DataCollector;
import lombok.Data;

import javax.persistence.MappedSuperclass;

/**
 * @author dyanjun
 * @date 2021/10/31 18:52
 */
@Data
@MappedSuperclass
public abstract class DataCollectorPO {

    private String protocol;

    public abstract DataCollectorPO convertDomain2PO(DataCollector collector);

    public abstract DataCollector convertPO2Domain(DataCollectorPO collectorPO);

}
