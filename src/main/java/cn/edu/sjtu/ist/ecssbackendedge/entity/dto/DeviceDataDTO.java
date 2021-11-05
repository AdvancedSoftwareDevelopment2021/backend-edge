package cn.edu.sjtu.ist.ecssbackendedge.entity.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceDataDTO {

    /**
     * 设备数据id
     */
    private String id;

    /**
     * 对应设备id
     */
    private String deviceId;

    /**
     * 数据的时间
     */
    private Date timestamp;

    /**
     * 数据
     */
    private String data;

}
