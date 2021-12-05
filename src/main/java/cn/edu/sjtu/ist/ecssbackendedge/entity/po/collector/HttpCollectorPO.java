package cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector;

import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.HttpCollector;
import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/11/19 17:55
 */
@Data
public class HttpCollectorPO extends DataCollectorPO {

    private String url;

    @Override
    public HttpCollector convertPO2Domain(DataCollectorPO dataCollectorPO) {
        HttpCollectorPO collectorPO = (HttpCollectorPO) dataCollectorPO;
        HttpCollector collector = new HttpCollector();
        collector.setUrl(collectorPO.getUrl());
        return collector;
    }
}
