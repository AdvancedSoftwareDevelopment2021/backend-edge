package cn.edu.sjtu.ist.ecssbackendedge.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备数据DTO
 * @date 2021-11-08
 */
@Data
public class DeviceDataDTO extends BaseRowModel {

    /**
     * 对应设备id
     */
    @ExcelProperty(value = {"设备编号"}, index = 0)
    private String deviceId;

    /**
     * 对应的sensor名称
     */
    @ExcelProperty(value = {"sensor名称"}, index = 1)
    private String sensorName;

    /**
     * 数据的时间
     */
    @ExcelProperty(value = {"时间"}, index = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Date timestamp;

    /**
     * 数据
     */
    @ExcelProperty(value = {"设备数据"}, index = 3)
    private String data;

}
