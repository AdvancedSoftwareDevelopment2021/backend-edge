package cn.edu.sjtu.ist.ecssbackendedge.model;

import lombok.Data;

import java.util.Date;

/**
 * 设备的实时状态数据
 */
@Data
public class DeviceStatus {

    /**
     * 状态数据id
     */
    private long id;

    /**
     * 对应的设备id
     */
    private long deviceId;

    /**
     * 对应时刻
     */
    private Date timestamp;

    /**
     * 状态信息
     */
    private String status;
}
