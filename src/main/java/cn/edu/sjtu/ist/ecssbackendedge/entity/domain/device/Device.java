package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.Command;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备
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

    /**
     * 设备的指令列表
     */
    private List<Command> commands;

    /**
     * 将collector收集到的原始数据包装为key-value形式返回存储
     *
     * @param data 收集到的数据
     */
    public String wrapData(String data) {
        return data;
    }
}
