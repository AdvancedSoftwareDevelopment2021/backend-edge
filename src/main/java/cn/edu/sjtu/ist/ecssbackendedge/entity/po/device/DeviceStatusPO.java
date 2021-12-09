package cn.edu.sjtu.ist.ecssbackendedge.entity.po.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备状态PO
 * @date 2021-11-08
 */
@Data
@Profile("mongodb")
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
     * 对应的sensor名称
     */
    @Field
    private String sensorName;

    /**
     * 对应时刻
     */
    @Field
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp;

    /**
     * 状态信息
     */
    @Field
    private String status;
}
