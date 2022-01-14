package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.machineLearning.MachineLearning;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.*;

/**
 * @author dyanjun
 * @date 2022/1/14 0:40
 */
public interface MachineLearningRepository extends MongoRepository<MachineLearning, String> {
    List<MachineLearning> findAll();
}
