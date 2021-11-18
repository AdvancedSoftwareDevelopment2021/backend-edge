package cn.edu.sjtu.ist.ecssbackendedge.model.device;

import lombok.Data;

import java.util.List;

/**
 * @brief 设备
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
@Data
public class Device {

    /**
     * 设备id
     */
    private String id;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 设备描述，可有可无
     */
    private String description;

    /**
     * 设备的数据项列表
     */
    private List<DataEntry> values;
}