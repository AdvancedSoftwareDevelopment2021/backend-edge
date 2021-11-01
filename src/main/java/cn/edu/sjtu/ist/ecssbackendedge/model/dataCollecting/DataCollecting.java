package cn.edu.sjtu.ist.ecssbackendedge.model.dataCollecting;

import cn.edu.sjtu.ist.ecssbackendedge.component.QuartzScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.component.factory.DataCollectingFactory;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.service.DataCollectingService;
import lombok.Data;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * @author dyanjun
 * @date 2021/10/31 18:38
 */
@Data
@Slf4j
public class DataCollecting {
    private String id;

    private String name;

    private CollectScheduler collectorScheduler;

    private Status status = Status.SLEEP;

    private DataCollector dataCollector;

    private QuartzScheduler quartzScheduler;

    private DataCollectingService service;

    private DataCollectingFactory dataCollectingFactory;

    private void collectData() throws Exception {
        log.info("{}开始采集数据", this.name);
        this.status = Status.RUNNING;
        service.syncStatus(this);
        if (!this.dataCollector.execute(id)) {
            this.status = Status.FAILURE;
            service.syncStatus(this);
        }
        else {
            this.status = Status.SLEEP;
            service.syncStatus(this);
        }
        log.info("{}采集数据结束", this.name);
    }

    public void schedule(){
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("id", this.id);
        jobDataMap.put("factory", this.dataCollectingFactory);
        JobDetail job = JobBuilder.newJob(CollectDataJob.class)
                .withIdentity(id)
                .usingJobData(jobDataMap)
                .build();
        Trigger trigger = collectorScheduler.generateTrigger();
        try {
            this.quartzScheduler.getScheduler().scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException("数据采集任务调度失败", e);
        }
    }

    public void stopSchedule() throws SchedulerException {
        quartzScheduler.getScheduler().deleteJob(JobKey.jobKey(id));
    }

    public static class CollectDataJob implements Job {
        @Setter
        String id;

        @Setter
        DataCollectingFactory factory;

        @SneakyThrows
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            factory.fromId(id).collectData();
        }
    }
}
