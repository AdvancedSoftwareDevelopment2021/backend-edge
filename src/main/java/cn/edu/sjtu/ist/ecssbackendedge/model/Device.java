package cn.edu.sjtu.ist.ecssbackendedge.model;

import cn.edu.sjtu.ist.ecssbackendedge.utils.MessageProtocol;

import lombok.Data;

@Data
public class Device {

    /**
     * 产品id
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
    private MessageProtocol messageProtocol;
}
