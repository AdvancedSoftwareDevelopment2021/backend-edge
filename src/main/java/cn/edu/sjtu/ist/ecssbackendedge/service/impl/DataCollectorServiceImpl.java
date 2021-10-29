package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.DataCollector;
import cn.edu.sjtu.ist.ecssbackendedge.repository.DataCollectorRepository;
import cn.edu.sjtu.ist.ecssbackendedge.service.DataCollectorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dyanjun
 * @date 2021/10/28 14:17
 */
@Service
public class DataCollectorServiceImpl implements DataCollectorService {

    @Autowired
    DataCollectorRepository dataCollectorRepository;

    @Override
    public Response insertDataCollector(DataCollector dataCollector){
        dataCollectorRepository.save(dataCollector);
        return new Response(200L, "OK", dataCollector.convert2DTO());
    }

    @Override
    public Response deleteDataCollector(String id){
        dataCollectorRepository.deleteById(id);
        return new Response(200L, "OK", "delete ok!");
    }

    @Override
    public Response updateDataCollector(DataCollector dataCollector){
        DataCollector newDataCollector = dataCollectorRepository.findDataCollectorById(dataCollector.getId());
        BeanUtils.copyProperties(dataCollector, newDataCollector);
        dataCollectorRepository.save(newDataCollector);
        return new Response(200L, "OK", newDataCollector.convert2DTO());
    }

    @Override
    public Response getDataCollectorById(String id){
        DataCollector dataCollector = dataCollectorRepository.findDataCollectorById(id);
        return new Response(200L, "OK", dataCollector.convert2DTO());
    }
}
