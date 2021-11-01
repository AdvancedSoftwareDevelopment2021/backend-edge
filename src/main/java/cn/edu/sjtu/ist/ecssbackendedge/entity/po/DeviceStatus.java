package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 设备的实时状态数据
 */
@Data
@Entity
public class DeviceStatus {

    /**
     * 状态数据id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 对应的设备id
     */
    @ManyToOne
    private Device device;

    /**
     * 对应时刻
     */
    private Date timestamp;

    /**
     * 状态信息
     */
    private String status;
}
