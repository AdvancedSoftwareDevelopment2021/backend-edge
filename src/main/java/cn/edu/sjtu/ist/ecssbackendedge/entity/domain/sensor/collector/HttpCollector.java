package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.collector;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collector.HttpCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.MessageProtocol;
import cn.edu.sjtu.ist.ecssbackendedge.utils.collect.HttpClientPoolUtil;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dyanjun
 * @date 2021/11/19 17:42
 */
@Slf4j
@Data
@NoArgsConstructor
public class HttpCollector extends DataCollector {

    private String url;

    @Override
    public String execute(String id) {
        return HttpClientPoolUtil.doGet(id, url);
    }

    @Override
    protected void verify() {
        if (url == null) {
            throw new RuntimeException("url不能为空");
        }
    }

    @Override
    public HttpCollectorPO convertDomain2PO() {

        HttpCollectorPO collectorPO = new HttpCollectorPO();

        collectorPO.setType(MessageProtocol.HTTP.getProtocol());
        collectorPO.setUrl(url);

        return collectorPO;
    }

    @Override
    public Boolean monitor(String id) {
        log.error("http无法监听");
        return false;
    }

    @Override
    public Boolean stopMonitor(String id) {
        log.error("http无法停止监听");
        return false;
    }
}
