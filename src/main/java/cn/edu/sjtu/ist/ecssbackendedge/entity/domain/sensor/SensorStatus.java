package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor;

import lombok.Data;

import java.util.Date;

@Data
public class SensorStatus {

    private Date timestamp;

    private String status;

}
