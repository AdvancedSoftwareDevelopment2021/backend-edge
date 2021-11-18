package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.Date;

/**
 * @brief 设备状态PO
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
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
    private String deviceId;

    /**
     * 对应时刻
     */
    @Field
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;

    /**
     * 状态信息
     */
    @Field
    private String status;
}
