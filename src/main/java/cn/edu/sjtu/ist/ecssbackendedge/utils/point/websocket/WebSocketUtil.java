package cn.edu.sjtu.ist.ecssbackendedge.utils.point.websocket;

import cn.edu.sjtu.ist.ecssbackendedge.config.WebSocketClientConfig;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.enums.ReadyState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.util.Map;

/**
 * @author dyanjun
 * @date 2021/11/20 14:29
 */
@Slf4j
@Component
public class WebSocketUtil {
    @Autowired
    WebSocketClientConfig webSocketClientConfig;

    public void getData(String id, String uri) {
        try {
            MyWebSocketClient client = webSocketClientConfig.getClient(id, uri);
            if (client.getReadyState().equals(ReadyState.OPEN)) {
                client.send("采集数据");
            }
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    public void startMonitor(String id, String uri) {
        try {
            MyWebSocketClient client = webSocketClientConfig.getClient(id, uri);
        } catch (URISyntaxException e) {
            log.error(e.getMessage());
        }
    }

    public void stopMonitor(String id) {
        webSocketClientConfig.deleteClient(id);
    }
}
