package cn.edu.sjtu.ist.ecssbackendedge.entity.po.device;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device.DataEntry;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备PO
 * @date 2021-11-08
 */
@Data
@Document(collection = "device")
public class DevicePO {

    /**
     * 设备id
     */
    @Id
    private String id;

    /**
     * 设备名称
     */
    @Field
    private String name;

    /**
     * 设备型号
     */
    @Field
    private String model;

    /**
     * 设备描述，可有可无
     */
    @Field
    private String description;

    /**
     * 设备的数据项列表
     */
    @Field
    private List<DataEntry> values;
}
