package cn.edu.sjtu.ist.ecssbackendedge.entity.po.modbus;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author dyanjun
 * @date 2021/10/27 20:56
 */
@Data
@Entity
public class ModbusCollector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    @NotNull
    private String ip;
    @NotNull
    private Integer port;
    @NotNull
    private Integer ipType;

    private Integer deviceDataGatherCycle;
    private Integer deviceDataGatherInterval;
    private Integer noteAlarmDataGatherCycle;
    private Integer environmentDataRecordCycle;

}
