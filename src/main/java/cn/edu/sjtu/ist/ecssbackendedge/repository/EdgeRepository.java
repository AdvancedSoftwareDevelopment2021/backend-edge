package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.EdgePO;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EdgeRepository extends MongoRepository<EdgePO, String> {

    List<EdgePO> findAll();

}
