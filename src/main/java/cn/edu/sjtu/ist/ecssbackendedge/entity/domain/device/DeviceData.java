package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备数据
 * @date 2021-11-08
 */
@Data
public class DeviceData {

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
