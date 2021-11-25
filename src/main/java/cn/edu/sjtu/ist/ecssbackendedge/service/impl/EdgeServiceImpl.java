package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.model.Edge;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.service.EdgeService;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO: 将采集到的数据定时发送到云端
@Slf4j
@Service
public class EdgeServiceImpl implements EdgeService {

    @Autowired
    private Edge edge;

    @Override
    public Response startEdge(CollectScheduler scheduler) {
        try {
            edge.setCollectorScheduler(scheduler);
            edge.startPackageData(); // 开始收集数据
            return new Response(200, "Edge收集数据，启动成功！", null);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new Response(200, "Edge收集数据，启动失败！", null);
        }
    }

    @Override
    public Response stopEdge() {
        try {
            edge.stopPackageData(); // 结束数据打包任务
            return new Response(200, "Edge收集数据，关闭成功！", null);
        } catch (SchedulerException e) {
            log.info(e.getMessage());
            return new Response(200, "Edge收集数据，关闭失败！", null);
        }
    }

    @Override
    public Response restartEdge(CollectScheduler scheduler) {
        try {
            edge.stopPackageData(); // 结束数据打包任务
            edge.setCollectorScheduler(scheduler);
            edge.startPackageData(); // 重新开始收集数据
            return new Response(200, "Edge收集数据，重启成功！", null);
        } catch (SchedulerException e) {
            log.info(e.getMessage());
            return new Response(200, "Edge收集数据，重启失败！", null);
        }
    }
}
