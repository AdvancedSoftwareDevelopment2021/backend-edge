package cn.edu.sjtu.ist.ecssbackendedge.entity.domain;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.scheduler.QuartzScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.utils.compress.PackageUtil;
import cn.edu.sjtu.ist.ecssbackendedge.utils.connect.ConnectCloudUtil;

import lombok.Data;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Data
@Component
public class Edge {

    @Autowired
    private QuartzScheduler quartzScheduler;

    @Autowired
    private PackageUtil packageUtil;

    @Autowired
    private ConnectCloudUtil connectCloudUtil;

    private String id;

    private final String jobId = UUID.randomUUID().toString();

    private Date lastTime;

    private CollectScheduler collectorScheduler;

    public void setCollectorScheduler(CollectScheduler collectorScheduler) {
        collectorScheduler.setStartTime(this.lastTime == null ? new Date() : this.lastTime);
        this.collectorScheduler = collectorScheduler;
    }

    /**
     * 主函数，开启数据打包任务
     */
    public void startPackageData() {
        this.lastTime = new Date();

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("edge", this);
        JobDetail job = JobBuilder.newJob(PackageDataJob.class)
                .withIdentity(this.jobId)
                .usingJobData(jobDataMap)
                .build();
        Trigger trigger = collectorScheduler.generateTrigger();
        try {
            this.quartzScheduler.getScheduler().scheduleJob(job, trigger);
            log.info("Edge开始收集数据！");
        } catch (SchedulerException e) {
            throw new RuntimeException("Edge启动失败！", e);
        }
    }

    /**
     * 主函数，停止数据打包任务
     */
    public void stopPackageData() throws SchedulerException {
        this.quartzScheduler.getScheduler().deleteJob(JobKey.jobKey(this.jobId));
        log.info("Edge关闭成功！");
    }

    public String getAllDeviceHistoryData() {
        String filepath = packageUtil.getFilepath(String.format("test_%d", (new Date()).getTime()));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime = formatter.format(this.lastTime);
        String endTime = formatter.format(new Date());
        this.lastTime = new Date();

        boolean ret = packageUtil.packageDeviceHistoryData(filepath, startTime, endTime);
        if (!ret) {
            throw new RuntimeException(String.format("设备历史数据写入失败，起始时间-结束时间：%s-%s", startTime, endTime));
        }
        return filepath;
    }

    public static class PackageDataJob implements Job {
        @Setter
        Edge edge;

        @SneakyThrows
        @Override
        public void execute(JobExecutionContext context) {
            String filepath = edge.getAllDeviceHistoryData();
            log.info(String.format("边缘端收集到的数据路径: %s", filepath));
            edge.connectCloudUtil.sendDataPackage(edge.getId(), filepath);
            log.info(String.format("边缘端设备数据包上传成功，时间：%s", edge.lastTime));
        }
    }
}
