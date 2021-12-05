package cn.edu.sjtu.ist.ecssbackendedge.service;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.response.CommandResponse;
import cn.edu.sjtu.ist.ecssbackendedge.model.process.Process;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;

import java.util.Map;

public interface EdgeService {

    CommandResponse startEdge(CollectScheduler scheduler);

    CommandResponse stopEdge();

    CommandResponse restartEdge(CollectScheduler scheduler);

    CommandResponse setCloudUrl(Map<String, String> map);

    CommandResponse processControl(Process process);
}
