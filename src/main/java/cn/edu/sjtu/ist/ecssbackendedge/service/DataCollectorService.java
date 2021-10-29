package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollector.DataCollector;

/**
 * @author dyanjun
 * @date 2021/10/28 14:18
 */
public interface DataCollectorService {

    Response insertDataCollector(DataCollector dataCollector);

    Response deleteDataCollector(String id);

    Response updateDataCollector(DataCollector dataCollector);

    Response getDataCollectorById(String id);
}
