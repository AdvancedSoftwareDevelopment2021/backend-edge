package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.scheduler.CollectSchedulerPO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollectSchedulerRepository extends MongoRepository<CollectSchedulerPO, String> {

    CollectSchedulerPO findCollectSchedulerPOById(String id);

}
