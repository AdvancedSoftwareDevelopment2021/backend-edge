package cn.edu.sjtu.ist.ecssbackendedge.config;

import org.apache.iotdb.session.Session;
import org.apache.iotdb.session.pool.SessionPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IotDBSessionConfig {

    @Value("${spring.iotdb.ip}")
    private String ip;

    @Value("${spring.iotdb.port}")
    private int port;

    @Value("${spring.iotdb.user}")
    private String user;

    @Value("${spring.iotdb.password}")
    private String password;

    @Value("${spring.iotdb.fetchSize}")
    private int fetchSize;

    @Value("${spring.iotdb.maxSize}")
    private int maxSize;

    @Bean
    public Session getIotdbSession() {
        return new Session(ip, port, user, password, fetchSize);
    }

    @Bean
    public SessionPool getIotdbSessionPool() {
        return new SessionPool(ip, port, user, password, maxSize);
    }
}
