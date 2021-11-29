package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.EdgeDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.Response;
import cn.edu.sjtu.ist.ecssbackendedge.model.Edge;
import cn.edu.sjtu.ist.ecssbackendedge.model.process.Process;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.TimeUnit;
import cn.edu.sjtu.ist.ecssbackendedge.service.EdgeService;
import cn.edu.sjtu.ist.ecssbackendedge.utils.connect.ConnectCloudUtil;

import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class EdgeServiceImpl implements EdgeService {

    @Autowired
    private Edge edge;

    @Autowired
    private EdgeDao edgeDao;

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

    @Override
    public Response setCloudUrl(Map<String, String> map) {
        String url = map.get("url");
        String id = map.get("id");
        int interval = Integer.parseInt(map.get("interval"));
        TimeUnit unit = TimeUnit.valueOf(map.get("timeUnit"));

        ConnectCloudUtil.CLOUD_SERVER_URL = url;
        CollectScheduler scheduler = new CollectScheduler();
        scheduler.setInterval(interval);
        scheduler.setUnit(unit);
        edge.setId(id);
        edge.setCollectorScheduler(scheduler);
        edgeDao.insertAndUpdate(edge);
        log.info(String.format("id: %s", edge.getId()));
        log.info(String.format("createTime: %s", edge.getLastTime()));
        log.info(String.format("collectorScheduler: %s", edge.getCollectorScheduler().toString()));
        log.info(String.format("url: %s", ConnectCloudUtil.CLOUD_SERVER_URL));

        edge.startPackageData();

        return new Response(200, String.format("接收并保存云端信息成功, url=%s", url), null);
    }

    @Override
    public Response processControl(Process process) {
        return new Response(200, "流程控制成功", null);
    }
}
