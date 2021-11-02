package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.collecting.AddDataCollectingRequest;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.collecting.DataCollectingPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting.DataCollecting;

/**
 * @author dyanjun
 * @date 2021/10/28 14:18
 */
public interface DataCollectingService {

    void syncStatus(DataCollecting dataCollecting);

    DataCollectingPO insertDataCollecting(AddDataCollectingRequest request);

    void deleteDataCollecting(String id);

    DataCollectingPO getDataCollectingById(String id);
}
