package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

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
     * 数据的时间
     */
    @Field
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    /**
     * 数据
     */
    @Field
    private String data;

}
