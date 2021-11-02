package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collecting.DataCollectingPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author dyanjun
 * @date 2021/10/28 14:32
 */
@Repository
public interface DataCollectingRepository extends MongoRepository<DataCollectingPO, String> {
    Optional<DataCollectingPO> findById(String id);
}