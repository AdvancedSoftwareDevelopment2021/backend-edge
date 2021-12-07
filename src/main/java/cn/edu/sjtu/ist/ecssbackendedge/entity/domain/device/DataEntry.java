package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device;

import lombok.Data;

@Data
public class DataEntry {

    /**
     * 数据项的名称
     */
    private String name;

    /**
     * 数据项对应的sensor
     */
    private String sensorId;

    /**
     * 数据项的类型
     */
    private String type;

    /**
     * 数据项的通信协议
     */
    private String protocol;
}
