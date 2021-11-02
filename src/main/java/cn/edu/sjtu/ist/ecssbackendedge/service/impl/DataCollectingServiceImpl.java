package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.component.factory.DataCollectingFactory;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.collecting.AddDataCollectingRequest;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collecting.DataCollectingPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collecting.DataCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.scheduler.CollectSchedulerPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.DataCollecting;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.Status;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DataCollectingRepository;
import cn.edu.sjtu.ist.ecssbackendedge.service.DataCollectingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author dyanjun
 * @date 2021/10/28 14:17
 */
@Service
public class DataCollectingServiceImpl implements DataCollectingService {

    @Autowired
    DataCollectingRepository dataCollectingRepository;

    @Autowired
    DataCollectingFactory dataCollectingFactory;

    @Override
    public void syncStatus(DataCollecting dataCollecting) {
        DataCollectingPO po = dataCollectingRepository.findById(dataCollecting.getId()).orElseThrow(() -> new RuntimeException("数据采集过程不存在"));
        po.syncStatusWith(dataCollecting);
        dataCollectingRepository.save(po);
    }

    @Override
    public DataCollectingPO insertDataCollecting(AddDataCollectingRequest request) {
        DataCollectingPO po = convertRequest2PO(request);
        dataCollectingRepository.save(po);
        DataCollecting dataCollecting = dataCollectingFactory.fromPO(po);
        try {
            dataCollecting.schedule(); // 开始采集数据
        } catch (Exception e) {
            dataCollectingRepository.deleteById(dataCollecting.getId());
            throw new RuntimeException(e);
        }
        return po;
    }

    @Override
    public void deleteDataCollecting(String id) {
        dataCollectingRepository.deleteById(id);
    }

    @Override
    public DataCollectingPO getDataCollectingById(String id) {
        return dataCollectingRepository.findById(id).orElseThrow(() -> new RuntimeException("数据采集过程不存在"));
    }

    /**
     * 将添加数据采集过程请求转换成对应的 po
     *
     * @param request 添加数据采集过程请求
     * @return 对应的 po
     */
    private DataCollectingPO convertRequest2PO(AddDataCollectingRequest request) {
        DataCollectingPO dataCollectingPO = new DataCollectingPO();
        dataCollectingPO.setCreatedTime(new Date());
        dataCollectingPO.setStatus(Status.SLEEP);
        dataCollectingPO.setName(request.getName());

        CollectSchedulerPO schedulerPO = request.getCollectScheduler().convert2PO();
        DataCollectorPO dataCollectorPO = request.getDataCollector().convert2PO();
        dataCollectingPO.setCollectorScheduler(schedulerPO);
        dataCollectingPO.setDataCollector(dataCollectorPO);

        return dataCollectingPO;
    }
}
