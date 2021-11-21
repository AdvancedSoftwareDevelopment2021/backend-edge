package cn.edu.sjtu.ist.ecssbackendedge.model.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @brief 设备状态PO
 * @author rsp
 * @version 0.1
 * @date 2021-11-08
 */
@Data
public class DeviceStatus {

    /**
     * 状态数据id
     */
    private String id;

    /**
     * 对应的设备id
     */
    private String deviceId;

    /**
     * 对应的sensor名称
     */
    private String sensorName;

    /**
     * 对应时刻
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date timestamp;

    /**
     * 状态信息
     */
    private String status;
}
