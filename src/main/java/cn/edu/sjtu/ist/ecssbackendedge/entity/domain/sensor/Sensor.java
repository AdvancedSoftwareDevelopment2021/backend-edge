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

    private DeviceStatusDao deviceStatusDao;

    public void monitor() {
        log.info("开始监听{}", this.name);
        this.status = Status.RUNNING;
        this.sensorDao.updateSensorStatus(this.id, this.status);
        this.dataCollector.monitor(id);
    }

    public void stopMonitor() {
        log.info("停止监听{}", this.name);
        this.status = Status.SLEEP;
        this.sensorDao.updateSensorStatus(this.id, this.status);
        this.dataCollector.stopMonitor(id);
    }

    private String collectData() {
        log.info("开始采集数据项{}", this.name);
        this.status = Status.RUNNING;
        this.sensorDao.updateSensorStatus(this.id, this.status);

        String collectedData = this.dataCollector.execute(id);
        if (collectedData == null) {
            this.status = Status.FAILURE;
            // TODO 采集失败异常处理
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
            log.info("sensor " + name + "开启成功！");
        } catch (SchedulerException e) {
            throw new RuntimeException("数据采集任务调度失败", e);
        }
    }

    public void stopSchedule() throws SchedulerException {
        quartzScheduler.getScheduler().deleteJob(JobKey.jobKey(id));
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
            sensor.deviceStatusDao.saveDeviceStatus(sensor.deviceId, sensor.name, sensor.status.getType());
        }
    }
}
