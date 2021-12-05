package cn.edu.sjtu.ist.ecssbackendedge.utils.collect.websocket;


import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.Sensor;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.quartz.SchedulerException;

import java.net.URI;

/**
 * @author dyanjun
 * @date 2021/11/20 14:09
 */
@Slf4j
public class MyWebSocketClient extends WebSocketClient {

    private Sensor sensor;

    public MyWebSocketClient(URI serverUri, Sensor sensor) {
        super(serverUri);
        this.sensor = sensor;
    }


    @Override
    public void onOpen(ServerHandshake arg0) {
        log.info(sensor.getName() + " 端口打开");
    }


    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        log.info(sensor.getName() + " 端口关闭");
        try {
            sensor.stopSchedule();
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onError(Exception arg0) {
        log.info(sensor.getName() + " 连接错误");
        try {
            sensor.stopSchedule();
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onMessage(String message) {
        log.info(sensor.getName() + ": " + message);
        if (message != null && !message.equals("null")) {
            // TODO 保存数据的方式有待商榷
            sensor.getDeviceDataDao().saveDeviceData(sensor.getDeviceId(), sensor.getName(), "\"" + sensor.getName() + "\":" + message);
        }
        sensor.getDeviceStatusDao().saveDeviceStatus(sensor.getDeviceId(), sensor.getName(), sensor.getStatus().getType());
    }

}
