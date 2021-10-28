package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.dataCollector.DataCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.DataCollector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author dyanjun
 * @date 2021/10/28 14:32
 */
public interface DataCollectorRepository extends JpaRepository<DataCollectorPO, String> {

    DataCollectorPO findDataCollectorById(String id);

    void deleteById(String id);

    List<DataCollectorPO> findDataCollectorsByName(String name);
}