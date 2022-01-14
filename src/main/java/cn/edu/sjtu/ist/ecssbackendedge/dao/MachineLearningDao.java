package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.machineLearning.MachineLearning;
import java.util.*;

/**
 * @author dyanjun
 * @date 2022/1/14 0:38
 */
public interface MachineLearningDao {
    List<MachineLearning> getAllMachineLearning();

    MachineLearning insertMachineLearning(MachineLearning machineLearning);
}
