package cn.edu.sjtu.ist.ecssbackendedge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EcssBackendEdgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcssBackendEdgeApplication.class, args);
    }

}
