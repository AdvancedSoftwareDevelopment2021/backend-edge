package cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector;

import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.WebSocketCollector;
import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/11/20 20:15
 */
@Data
public class WebSocketCollectorPO extends DataCollectorPO{

    private String uri;

    @Override
    public WebSocketCollector convertPO2Domain(DataCollectorPO dataCollectorPO) {
        WebSocketCollectorPO collectorPO = (WebSocketCollectorPO) dataCollectorPO;
        WebSocketCollector collector = new WebSocketCollector();

        collector.setUri(collectorPO.getUri());

        return collector;
    }
}
