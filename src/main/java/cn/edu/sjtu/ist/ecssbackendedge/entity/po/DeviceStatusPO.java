package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.Date;

/**
 * 设备的实时状态数据
 */
@Data
@Document(collection = "device_status")
public class DeviceStatusPO {

    /**
     * 状态数据id
     */
    @Id
    private String id;

    /**
     * 对应的设备id
     */
    @Field
    private DevicePO device;

    /**
     * 对应时刻
     */
    @Field
    private Date timestamp;

    /**
     * 状态信息
     */
    @Field
    private String status;
}
