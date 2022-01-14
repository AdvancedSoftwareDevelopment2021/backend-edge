package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.MachineLearningDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.machineLearning.MachineLearning;
import cn.edu.sjtu.ist.ecssbackendedge.repository.MachineLearningRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dyanjun
 * @date 2022/1/14 0:38
 */
@Slf4j
@Component
public class MachineLearningDaoImpl implements MachineLearningDao {
    @Autowired
    MachineLearningRepository machineLearningRepository;

    @Override
    public List<MachineLearning> getAllMachineLearning() {
        return machineLearningRepository.findAll();
    }

    @Override
    public MachineLearning insertMachineLearning(MachineLearning machineLearning) {
        return machineLearningRepository.save(machineLearning);
    }
}
