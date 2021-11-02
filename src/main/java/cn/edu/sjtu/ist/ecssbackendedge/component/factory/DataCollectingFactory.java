package cn.edu.sjtu.ist.ecssbackendedge.component.factory;

import cn.edu.sjtu.ist.ecssbackendedge.component.QuartzScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collecting.DataCollectingPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.DataCollecting;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.DataCollector;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DataCollectingRepository;
import cn.edu.sjtu.ist.ecssbackendedge.service.DataCollectingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dyanjun
 * @date 2021/10/31 17:35
 */
@Component
public class DataCollectingFactory {
    @Autowired
    DataCollectorFactory dataCollectorFactory;

    @Autowired
    DataCollectingService dataCollectingService;

    @Autowired
    DataCollectingRepository dataCollectingRepository;

    @Autowired
    QuartzScheduler quartzScheduler;

    /**
     * 从 PO 生成数据采集过程对象
     *
     * @param po
     *            数据采集过程 PO
     * @return 数据采集过程对象
     */
    public DataCollecting fromPO(DataCollectingPO po) {
        DataCollecting res = new DataCollecting();
        basePropertyCopy(po, res);
        DataCollector dataCollector = dataCollectorFactory.fromPO(po.getDataCollector(), res);
        res.setDataCollector(dataCollector);
        res.setQuartzScheduler(quartzScheduler);
        res.setDataCollectingFactory(this);
        return res;
    }

    /**
     * 从 id 生成数据加工过程对象
     *
     * @param id
     *            数据加工过程 id
     * @return 数据加工过程对象
     */
    public DataCollecting fromId(String id) {
        DataCollectingPO po =
                dataCollectingRepository.findById(id).orElseThrow(() -> new RuntimeException("数据采集过程不存在"));
        return this.fromPO(po);
    }

    /**
     * 拷贝基本属性
     *
     * @param po
     *            数据加工过程 PO
     * @param dataProcessing
     *            数据加工过程对象
     */
    private void basePropertyCopy(DataCollectingPO po, DataCollecting dataProcessing) {
        dataProcessing.setId(po.getId());
        dataProcessing.setName(po.getName());
        dataProcessing.setStatus(po.getStatus());
        dataProcessing.setService(dataCollectingService);
        dataProcessing.setCollectorScheduler(po.getCollectorScheduler().convert2Domain());
    }
}
