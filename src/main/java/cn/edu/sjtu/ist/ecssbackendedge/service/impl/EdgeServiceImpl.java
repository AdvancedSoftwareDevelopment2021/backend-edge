package cn.edu.sjtu.ist.ecssbackendedge.service.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.EdgeDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.response.CommandResponse;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.Edge;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.Process;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.scheduler.TimeUnit;
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
    public CommandResponse startEdge(CollectScheduler scheduler) {
        try {
            edge.setCollectorScheduler(scheduler);
            edge.startPackageData(); // 开始收集数据
            return new CommandResponse(true, "Edge收集数据，启动成功！");
        } catch (Exception e) {
            log.info(e.getMessage());
            return new CommandResponse(false, "Edge收集数据，启动失败！");
        }
    }

    @Override
    public CommandResponse stopEdge() {
        try {
            edge.stopPackageData(); // 结束数据打包任务
            return new CommandResponse(true, "Edge收集数据，关闭成功！");
        } catch (SchedulerException e) {
            log.info(e.getMessage());
            return new CommandResponse(false, "Edge收集数据，关闭失败！");
        }
    }

    @Override
    public CommandResponse restartEdge(CollectScheduler scheduler) {
        try {
            edge.stopPackageData(); // 结束数据打包任务
            edge.setCollectorScheduler(scheduler);
            edge.startPackageData(); // 重新开始收集数据
            return new CommandResponse(true, "Edge收集数据，重启成功！");
        } catch (SchedulerException e) {
            log.info(e.getMessage());
            return new CommandResponse(false, "Edge收集数据，重启失败！");
        }
    }

    @Override
    public CommandResponse setCloudUrl(Map<String, String> map) {
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
        return new CommandResponse(true, String.format("接收并保存云端信息成功, url=%s", url));
    }

    @Override
    public CommandResponse processControl(Process process) {
        return new CommandResponse(true, "流程控制成功");
    }
}
