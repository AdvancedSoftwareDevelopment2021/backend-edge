package cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.modbus;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.dataCollector.ModbusCollectorDTO;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.DataCollector;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.Status;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.utils.ModbusUtil;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.NonNull;
import org.quartz.core.QuartzScheduler;
import com.serotonin.modbus4j.code.DataType;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import javax.persistence.Entity;

/**
 * @author dyanjun
 * @date 2021/10/27 20:56
 */
@Data
@NoArgsConstructor
public class ModbusCollector extends DataCollector{

    @NonNull
    private String ip;

    @NonNull
    private Integer port;

    private Integer slaveId;

    @NonNull
    private Integer offset;

    @NonNull
    private String datatype; //TODO

    private Integer num;

    @Resource
    private ModbusUtil modbusUtil;

    @Builder
    public ModbusCollector(String id, String name,CollectScheduler collectScheduler,
                           Status status,QuartzScheduler quartzScheduler){
        super.id = id;

        super.name = name;

        this.collectorScheduler = collectScheduler;

        this.status = status;

        this.quartzScheduler = quartzScheduler;

    }

    @Override
    public ModbusCollectorDTO convert2DTO() {
        ModbusCollectorDTO modbusCollectorDTO = new ModbusCollectorDTO();
        BeanUtils.copyProperties(this, modbusCollectorDTO, ModbusCollectorDTO.class);
        return modbusCollectorDTO;
    }

    @Override
    protected void execute() throws ModbusTransportException {
        modbusUtil.readCoilStatus(super.id, this.ip, this.port, this.offset, this.num);
    }

    @Override
    protected void verify() {
        if(ip == null){
            throw new RuntimeException("ip不能为空");
        }
        if(port == null){
            throw new RuntimeException("port不能为空");
        }
    }

}
