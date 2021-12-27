package cn.edu.sjtu.ist.ecssbackendedge.config;

import cn.edu.sjtu.ist.ecssbackendedge.dao.SensorDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.Sensor;
import cn.edu.sjtu.ist.ecssbackendedge.utils.point.websocket.MyWebSocketClient;

import org.java_websocket.enums.ReadyState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dyanjun
 * @date 2021/11/20 13:52
 */
@Configuration
public class WebSocketClientConfig {

    @Autowired
    @Qualifier("websocketHashMap")
    private ConcurrentHashMap<String, MyWebSocketClient> clientMap;

    @Bean
    public ConcurrentHashMap<String, MyWebSocketClient> websocketHashMap() {
        return new ConcurrentHashMap<>();
    }

    @Autowired
    private SensorDao sensorDao;


    /**
     * @Title getClient
     * @Description: 通过id获取对应的websocketClient
     * @params: [id, uri]
     * @return: MyWebSocketClient
     */
    public MyWebSocketClient getClient(String id, String uri) throws URISyntaxException {

        MyWebSocketClient client = clientMap.get(id);
        if (client == null) {
            setClient(id, uri);
            client = clientMap.get(id);
        }
        if (!client.isOpen()) {
            if (client.getReadyState().equals(ReadyState.NOT_YET_CONNECTED)) {
                try {
                    client.connect();
                } catch (IllegalStateException e) {
                }
            } else if (client.getReadyState().equals(ReadyState.CLOSING) || client.getReadyState().equals(ReadyState.CLOSED)) {
                client.reconnect();
            }
        }
        return client;

    }

    /**
     * @return void
     * @Title setClient
     * @Description: 设置id对应的websocketClient
     * @params [id, uri]
     */
    private void setClient(String id, String uri) throws URISyntaxException {
        Sensor sensor = sensorDao.findSensorById(id);
        MyWebSocketClient client = new MyWebSocketClient(new URI(uri), sensor);
        clientMap.put(id, client);
    }

    public void deleteClient(String id) {
        MyWebSocketClient client = clientMap.get(id);
        if (client == null) {
            return;
        }
        client.close();
        clientMap.remove(id);
    }
}
