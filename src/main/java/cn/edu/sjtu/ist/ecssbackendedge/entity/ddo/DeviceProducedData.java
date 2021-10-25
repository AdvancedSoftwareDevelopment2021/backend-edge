package cn.edu.sjtu.ist.ecssbackendedge.entity.ddo;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceProducedData {

    /**
     * 设备数据id
     */
    private Long id;

    /**
     * 对应设备id
     */
    private Long deviceId;

    /**
     * 数据的时间
     */
    private Date timestamp;

    /**
     * 数据
     */
    private String data;

}
