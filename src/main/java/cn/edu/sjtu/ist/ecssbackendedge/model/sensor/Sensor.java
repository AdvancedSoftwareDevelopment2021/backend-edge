package cn.edu.sjtu.ist.ecssbackendedge.model.sensor;

import cn.edu.sjtu.ist.ecssbackendedge.component.QuartzScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.dao.SensorDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.model.enumeration.Status;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.collector.DataCollector;
import cn.edu.sjtu.ist.ecssbackendedge.model.scheduler.CollectScheduler;

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

    private String collectData() throws Exception {
        log.info("开始采集数据项{}", this.name);
        this.status = Status.RUNNING;
        this.sensorDao.updateSensorStatus(this.id, this.status);

        String collectedData = this.dataCollector.execute(id);
        if (collectedData == null) {
            this.status = Status.FAILURE;
            this.sensorDao.updateSensorStatus(this.id, this.status);
        } else {
            this.status = Status.SLEEP;
            this.sensorDao.updateSensorStatus(this.id, this.status);
        }
        log.info("采集数据项{}结束", this.name);
        return collectedData;
    }

    public void schedule() {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("sensor", this);

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
        Sensor sensor;

        @SneakyThrows
        @Override
        public void execute(JobExecutionContext context) {
            String collectedData = sensor.collectData();
            if (collectedData != null) {
                // TODO 保存数据的方式有待商榷
                sensor.deviceDataDao.saveDeviceData(sensor.deviceId, "\"" + sensor.name + "\":" + collectedData);
            }
        }
    }
}
