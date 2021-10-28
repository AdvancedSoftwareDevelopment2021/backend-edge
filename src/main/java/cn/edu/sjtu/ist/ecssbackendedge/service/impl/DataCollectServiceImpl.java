package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.dataCollector.DataCollectorPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.DataCollector;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DataCollectorRepository;
import cn.edu.sjtu.ist.ecssbackendedge.service.DataCollectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dyanjun
 * @date 2021/10/28 14:17
 */
@Service
public class DataCollectServiceImpl implements DataCollectService {

    @Autowired
    DataCollectorRepository dataCollectorRepository;

    @Override
    public Response insertDataCollector(DataCollector dataCollector){
        DataCollectorPO dataCollectorPO = new DataCollectorPO();
        BeanUtils.copyProperties(dataCollector,dataCollectorPO);
        dataCollectorRepository.save(dataCollectorPO);
        return new Response(200L, "OK", "insert ok!");
    }

    @Override
    public Response deleteDataCollector(String id){
        dataCollectorRepository.deleteById(id);
        return new Response(200L, "OK", "delete ok!");
    }

    @Override
    public Response updateDataCollector(String id, DataCollector dataCollector){
        DataCollectorPO dataCollectorPO = dataCollectorRepository.findDataCollectorById(id);
        BeanUtils.copyProperties(dataCollector,dataCollectorPO);
        dataCollectorRepository.save(dataCollectorPO);
        return new Response(200L, "OK", "update ok!");
    }

    @Override
    public Response getDataCollectorById(String id){
        DataCollectorPO dataCollectorPO = dataCollectorRepository.findDataCollectorById(id);
        DataCollector dataCollector = new DataCollector();
        return new Response(200L, "OK", dataCollectorPO);
    }
}
