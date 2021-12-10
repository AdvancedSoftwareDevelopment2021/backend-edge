package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.scheduler.QuartzScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceStatusDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.SensorDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.AsynDataStatus;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.Status;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.scheduler.CollectScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.collector.DataCollector;

import lombok.Data;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.util.Assert;

/**
 * @author dyanjun
 * @date 2021/10/31 18:38
 */
@Data
@Slf4j
public class Sensor {

    private String id;

    private String deviceId;

    private String name;

    private Status status = Status.SLEEP;

    private CollectScheduler collectorScheduler;

    private DataCollector dataCollector;

    private QuartzScheduler quartzScheduler;

    private SensorDao sensorDao;

    private DeviceDataDao deviceDataDao;

    private void updateStatus(Status status) {
        this.status = status;
        this.sensorDao.saveSensorStatus(this.id, this.deviceId, this.status.getType());
    }

    public void monitor() {
        Assert.isTrue(this.status == Status.SLEEP, "该sensor已经在运行中");
        log.info("开始监听{}", this.name);
        this.dataCollector.monitor(id);
        updateStatus(Status.RUNNING);
    }

    public void stopMonitor() {
        Assert.isTrue(this.status != Status.SLEEP, "该sensor已经关闭");
        log.info("停止监听{}", this.name);
        this.dataCollector.stopMonitor(id);
        updateStatus(Status.SLEEP);
    }

    private String collectData() {
        Assert.isTrue(this.status == Status.RUNNING || this.status == Status.SUCCESS, "该sensor目前状态异常");
        log.info("开始采集数据项{}", this.name);
        updateStatus(Status.COLLECTING);

        String collectedData = this.dataCollector.execute(id);
        if (collectedData == null) {
            // TODO 采集失败异常处理
            updateStatus(Status.FAILURE);
        } else {
            updateStatus(Status.SUCCESS);
        }
        log.info("采集数据项{}结束", this.name);
        return collectedData;
    }

    public void schedule() {
        updateStatus(Status.RUNNING);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("sensor", this);

        JobDetail job = JobBuilder.newJob(CollectDataJob.class)
                .withIdentity(id)
                .usingJobData(jobDataMap)
                .build();
        Trigger trigger = collectorScheduler.generateTrigger();
        try {
            this.quartzScheduler.getScheduler().scheduleJob(job, trigger);
            log.info("sensor " + name + "开启成功！");
        } catch (SchedulerException e) {
            throw new RuntimeException("数据采集任务调度失败", e);
        }
    }

    public void stopSchedule() throws SchedulerException {
        quartzScheduler.getScheduler().deleteJob(JobKey.jobKey(id));
        updateStatus(Status.SLEEP);
        log.info("sensor " + name + "关闭成功！");
    }

    public static class CollectDataJob implements Job {
        @Setter
        Sensor sensor;

        @SneakyThrows
        @Override
        public void execute(JobExecutionContext context) {
            String collectedData = sensor.collectData();
            if (collectedData != null && !collectedData.equals("null") && !collectedData.equals(AsynDataStatus.WAITING_DATA.getDataStatus())) {
                // TODO 保存数据的方式有待商榷
                sensor.deviceDataDao.saveDeviceData(sensor.deviceId, sensor.name, "\"" + sensor.name + "\":" + collectedData);
            }
        }
    }
}
