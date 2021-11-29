package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.model.process.Process;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;

import java.util.Map;

public interface EdgeService {

    Response startEdge(CollectScheduler scheduler);

    Response stopEdge();

    Response restartEdge(CollectScheduler scheduler);

    Response setCloudUrl(Map<String, String> map);

    Response processControl(Process process);
}
