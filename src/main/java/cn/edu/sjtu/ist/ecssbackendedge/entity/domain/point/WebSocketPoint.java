package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.Param;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.point.WebSocketPointPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.AsynDataStatus;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.MessageProtocol;
import cn.edu.sjtu.ist.ecssbackendedge.utils.point.websocket.WebSocketUtil;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author dyanjun
 * @date 2021/11/20 13:47
 */
@Slf4j
@Data
@NoArgsConstructor
public class WebSocketPoint extends Point {

    private String uri;

    private WebSocketUtil webSocketUtil;

    /**
     * 主动请求数据
     */
    @Override
    public String execute(String id) {
        webSocketUtil.getData(id, uri);
        return AsynDataStatus.WAITING_DATA.getDataStatus();
    }

    @Override
    protected void verify() {
        if (uri == null) throw new RuntimeException("uri不能为空");
    }

    @Override
    public WebSocketPointPO convertDomain2PO() {
        WebSocketPointPO collectorPO = new WebSocketPointPO();

        collectorPO.setType(MessageProtocol.WEBSOCKET.getProtocol());
        collectorPO.setUri(uri);

        return collectorPO;
    }

    @Override
    public Boolean monitor(String id) {
        webSocketUtil.startMonitor(id, uri);
        return true;
    }

    @Override
    public Boolean stopMonitor(String id) {
        webSocketUtil.stopMonitor(id);
        return true;
    }

    @Override
    public Boolean executeCustomCommand(String id, List<Param> params) {
        log.error("websocket 无法发送自定义指令");
        return false;
    }

    @Override
    public Boolean executePropertyCommand(String id, String type, String value) {
        log.error("websocket 无法进行属性设置");
        return false;
    }

}
