package cn.edu.sjtu.ist.ecssbackendedge.component.factory;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collecting.DataCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.DataCollecting;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.DataCollector;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.collector.ModbusCollector;
import cn.edu.sjtu.ist.ecssbackendedge.utils.ModbusUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * @author dyanjun
 * @date 2021/10/31 18:57
 */
@Component
public class DataCollectorFactory {
   @Autowired
    ModbusUtil modbusUtil;
    /**
     * 从 PO 生成数据加工规则
     *
     * @param po
     *            数据采集器 PO
     * @param dataCollecting
     *            数据采集过程对象
     * @return
     */
    DataCollector fromPO(DataCollectorPO po, DataCollecting dataCollecting) {
        if (DataCollectorPO.ModbusType.equals(po.getType())) {
            ModbusCollector res = new ModbusCollector();
            res.setDataCollecting(dataCollecting);

            res.setIp(po.getIp());
            res.setPort(po.getPort());
            res.setSlaveId(po.getSlaveId());
            res.setOffset(po.getOffset());
            res.setModbusFunction(po.getModbusFunction());
            res.setDatatype(po.getDatatype());
            res.setNum(po.getNum());

            res.setModbusUtil(this.modbusUtil);
            return res;
        } else {
            throw new RuntimeException("规则类型不存在");
        }
    }
}
