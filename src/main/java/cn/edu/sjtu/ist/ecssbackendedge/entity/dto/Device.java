package cn.edu.sjtu.ist.ecssbackendedge.entity.dto;

import lombok.Data;

@Data
public class Device {

    /**
     * 设备数据id
     */
    private Long id;

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
