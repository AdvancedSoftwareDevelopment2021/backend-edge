package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.ProcessPO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProcessRepository extends MongoRepository<ProcessPO, String> {

    void deleteProcessPOById(String id);

    ProcessPO findProcessById(String id);

    List<ProcessPO> findProcessPOSByName(String name);

    List<ProcessPO> findAll();

}
