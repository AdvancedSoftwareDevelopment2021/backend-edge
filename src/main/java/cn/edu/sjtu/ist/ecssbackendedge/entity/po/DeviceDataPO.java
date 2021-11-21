package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @brief 设备数据PO
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
@Data
@Profile("mongodb")
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
    private String deviceId;

    /**
     * 对应的sensor名称
     */
    @Field
    private String sensorName;

    /**
     * 数据的时间
     */
    @Field
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp;

    /**
     * 数据
     */
    @Field
    private String data;

}
