package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.MLResultDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.MachineLearningDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.CommandData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.machineLearning.MachineLearning;
import cn.edu.sjtu.ist.ecssbackendedge.service.MachineLearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dyanjun
 * @date 2022/1/14 0:37
 */
@Service
public class MachineLearningServiceImpl implements MachineLearningService {

    @Autowired
    private MachineLearningDao machineLearningDao;

    @Autowired
    private MLResultDao mlResultDao;

    @Override
    public List<MachineLearning> getAllMachineLearning() {
        return machineLearningDao.getAllMachineLearning();
    }

    @Override
    public MachineLearning createMachineLearning(String name){
        List<MachineLearning> mls = getAllMachineLearning();
        for(MachineLearning ml: mls){
            if(ml.getName().equals(name)){
                return ml;
            }
        }
        MachineLearning machineLearning = new MachineLearning();
        machineLearning.setName(name);
        return machineLearningDao.insertMachineLearning(machineLearning);
    }

    @Override
    public List<CommandData> findDeviceAllMLResult(String deviceId, String name) {
        // TODO: 获取咋实现
        return mlResultDao.findMLAllHistoryResult(deviceId, name);
//        return mlResultDao.findMLResultWithTime();
    }
}
