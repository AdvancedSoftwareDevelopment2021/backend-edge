package cn.edu.sjtu.ist.ecssbackendedge.entity.dto;

import lombok.Data;

@Data
public class DeviceDTO {

    /**
     * 设备数据id
     */
    private String id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 产品型号
     */
    private String model;

    /**
     * 通信协议
     */
    private String messageProtocol;
}
