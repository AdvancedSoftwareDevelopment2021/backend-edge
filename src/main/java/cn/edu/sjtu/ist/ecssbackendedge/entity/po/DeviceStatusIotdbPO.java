package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @brief 设备状态PO
 * @author rsp
 * @version 0.1
 * @date 2021-11-19
 */
@Data
@Profile("dev")
public class DeviceStatusIotdbPO {

    /**
     * 状态数据id
     */
    private String id;

    /**
     * 对应的设备id
     */
    private String deviceId;

    /**
     * 对应的senser名称
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

    public static List<String> getMeasurements() {
        return Arrays.asList("deviceId", "sensorName", "status");
    }
}
