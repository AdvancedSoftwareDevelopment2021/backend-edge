package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class DeviceDataPO {

    /**
     * 设备数据id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 对应设备id
     */
    @ManyToOne
    private DevicePO device;

    /**
     * 数据的时间
     */
    private Date timestamp;

    /**
     * 数据
     */
    private String data;

}
