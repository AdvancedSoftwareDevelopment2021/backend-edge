package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.driver.DriverStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author dyanjun
 * @date 2021/12/26 23:24
 */
@Data
public class CommandData {
    /**
     * 对应设备id
     */
    private String deviceId;

    /**
     * 对应的driver名称
     */
    private String driverName;

    /**
     * 数据的时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date timestamp;

    /**
     * 执行情况
     */
    private DriverStatus status;

    /**
     * 参数
     */
    private String data;

    public static List<String> getMeasurements() {
        return Arrays.asList("deviceId", "driverName", "data", "status");
    }

}
