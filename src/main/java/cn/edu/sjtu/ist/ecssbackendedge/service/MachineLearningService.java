package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.CommandData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.machineLearning.MachineLearning;
import java.util.*;

/**
 * @author dyanjun
 * @date 2022/1/14 0:36
 */
public interface MachineLearningService {

    List<MachineLearning> getAllMachineLearning();

    MachineLearning createMachineLearning(String name);

    List<CommandData> findDeviceAllMLResult(String deviceId, String name);
}
