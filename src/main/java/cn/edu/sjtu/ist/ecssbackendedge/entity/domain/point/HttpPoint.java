package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.point.HttpPointPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.MessageProtocol;
import cn.edu.sjtu.ist.ecssbackendedge.utils.point.HttpClientPoolUtil;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author dyanjun
 * @date 2021/11/19 17:42
 */
@Slf4j
@Data
@NoArgsConstructor
public class HttpPoint extends Point {

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
    public HttpPointPO convertDomain2PO() {

        HttpPointPO collectorPO = new HttpPointPO();

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

    @Override
    public Boolean executeCustomCommand(String id, Map<String, Object> params) {
        HttpClientPoolUtil.doPost(url, params.toString());
        return true;
    }

    @Override
    public Boolean executePropertyCommand(String id, String type, String value) {
        log.error("http无法进行属性设置");
        return false;
    }
}
