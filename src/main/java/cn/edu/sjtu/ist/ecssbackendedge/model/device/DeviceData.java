package cn.edu.sjtu.ist.ecssbackendedge.model.device;

import lombok.Data;

import java.util.Date;

/**
 * @brief 设备数据
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
@Data
public class DeviceData {

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
