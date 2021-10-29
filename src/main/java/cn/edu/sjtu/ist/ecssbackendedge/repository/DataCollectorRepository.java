package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.DataCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dyanjun
 * @date 2021/10/28 14:32
 */
@Repository
public interface DataCollectorRepository extends MongoRepository<DataCollector, String> {

    DataCollector findDataCollectorById(String id);

    void deleteById(String id);

    List<DataCollector> findDataCollectorsByName(String name);
}