package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import org.springframework.stereotype.Component;

@Component
public interface CollectSchedulerDao {

    void createCollectScheduler(CollectScheduler collectScheduler);

    void deleteCollectScheduler(String id);

    void updateCollectScheduler(CollectScheduler collectScheduler);

    CollectScheduler getCollectScheduler(String id);
}
