package cn.edu.sjtu.ist.ecssbackendedge.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @brief 设备数据DTO
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
@Data
public class DeviceDataDTO {

    /**
     * 设备数据id
     */
    private String id;

    /**
     * 对应设备id
     */
    private String deviceId;

    /**
     * 对应的sensor名称
     */
    private String sensorName;

    /**
     * 数据的时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date timestamp;

    /**
     * 数据
     */
    private String data;

}
