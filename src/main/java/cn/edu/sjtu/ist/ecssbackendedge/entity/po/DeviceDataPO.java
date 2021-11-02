package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "device_data")
public class DeviceDataPO {

    /**
     * 设备数据id
     */
    @Id
    private String id;

    /**
     * 对应设备id
     */
    @Field
    private DevicePO device;

    /**
     * 数据的时间
     */
    @Field
    private Date timestamp;

    /**
     * 数据
     */
    @Field
    private String data;

}
