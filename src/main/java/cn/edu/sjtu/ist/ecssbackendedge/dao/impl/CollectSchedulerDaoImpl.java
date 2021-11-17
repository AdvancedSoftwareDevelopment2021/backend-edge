package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.CollectSchedulerDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.scheduler.CollectSchedulerPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.repository.CollectSchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CollectSchedulerDaoImpl implements CollectSchedulerDao {

    @Autowired
    private CollectSchedulerRepository repository;

    @Override
    public void createCollectScheduler(CollectScheduler collectScheduler) {
        repository.save(convertDomain2PO(collectScheduler));
    }

    @Override
    public void deleteCollectScheduler(String id) {
        repository.deleteById(id);
    }

    @Override
    public void updateCollectScheduler(CollectScheduler collectScheduler) {
        CollectSchedulerPO collectSchedulerPO = repository.findById(collectScheduler.getId()).orElseThrow(() -> new RuntimeException("数据采集器不存在"));
        collectSchedulerPO.setInterval(collectScheduler.getInterval());
        collectSchedulerPO.setUnit(collectScheduler.getUnit());
        collectSchedulerPO.setStartTime(collectScheduler.getStartTime());
        repository.save(collectSchedulerPO);
    }

    @Override
    public CollectScheduler getCollectScheduler(String id) {
        CollectSchedulerPO collectSchedulerPO = repository.findById(id).orElseThrow(() -> new RuntimeException("数据采集器不存在"));
        return convertPO2Domain(collectSchedulerPO);
    }

    private CollectSchedulerPO convertDomain2PO(CollectScheduler collectScheduler) {
        CollectSchedulerPO res = new CollectSchedulerPO();
        res.setId(collectScheduler.getId());
        res.setInterval(collectScheduler.getInterval());
        res.setUnit(collectScheduler.getUnit());
        res.setStartTime(collectScheduler.getStartTime());
        return res;
    }

    private CollectScheduler convertPO2Domain(CollectSchedulerPO collectSchedulerPO) {
        CollectScheduler res = new CollectScheduler();
        res.setId(collectSchedulerPO.getId());
        res.setInterval(collectSchedulerPO.getInterval());
        res.setUnit(collectSchedulerPO.getUnit());
        res.setStartTime(collectSchedulerPO.getStartTime());
        return res;
    }
}
