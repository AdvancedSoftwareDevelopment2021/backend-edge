package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;

public interface EdgeService {

    Response startEdge(CollectScheduler scheduler);

    Response stopEdge();

    Response restartEdge(CollectScheduler scheduler);
}
