package cn.edu.sjtu.ist.ecssbackendedge.utils.convert;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.scheduler.QuartzScheduler;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.SensorDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.request.SensorRequest;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.response.SensorResponse;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.sensor.SensorPO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.Sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author rsp
 * @version 0.1
 * @brief Sensor对象转换工具类
 * @date 2021-11-20
 */
@Component
public class SensorUtil {

    @Autowired
    private CollectSchedulerUtil collectSchedulerUtil;

    @Autowired
    private PointUtil pointUtil;

    @Autowired
    private QuartzScheduler quartzScheduler;

    @Autowired
    private DeviceDataDao deviceDataDao;

    @Autowired
    private SensorDao sensorDao;

    public Sensor convertRequestDTO2Domain(SensorRequest request) {
        Sensor res = new Sensor();
        res.setName(request.getName());
        res.setCollectorScheduler(request.getCollectScheduler());
        res.setPoint(request.getPoint());
        res.setQuartzScheduler(quartzScheduler);
        res.setSensorDao(sensorDao);
        res.setDeviceDataDao(deviceDataDao);
        return res;
    }

    public SensorResponse convertDomain2ResponseDTO(Sensor sensor) {
        SensorResponse res = new SensorResponse();
        res.setId(sensor.getId());
        res.setDeviceId(sensor.getDeviceId());
        res.setName(sensor.getName());
        res.setStatus(sensor.getStatus());
        res.setCollectorScheduler(sensor.getCollectorScheduler());
        res.setPoint(sensor.getPoint());
        return res;
    }

    public Sensor convertPO2Domain(SensorPO sensorPO) {
        Sensor res = new Sensor();
        res.setId(sensorPO.getId());
        res.setDeviceId(sensorPO.getDeviceId());
        res.setName(sensorPO.getName());
        res.setCollectorScheduler(collectSchedulerUtil.convertPO2Domain(sensorPO.getCollectorScheduler()));
        res.setPoint(pointUtil.convertPO2Domain(sensorPO.getDataCollector()));
        res.setQuartzScheduler(quartzScheduler);
        res.setSensorDao(sensorDao);
        res.setDeviceDataDao(deviceDataDao);
        return res;
    }

    public SensorPO convertDomain2PO(Sensor sensor) {
        SensorPO res = new SensorPO();
        res.setId(sensor.getId());
        res.setDeviceId(sensor.getDeviceId());
        res.setName(sensor.getName());
        res.setCreatedTime(new Date());
        res.setCollectorScheduler(collectSchedulerUtil.convertDomain2PO(sensor.getCollectorScheduler()));
        res.setDataCollector(pointUtil.convertDomain2PO(sensor.getPoint()));
        return res;
    }
}
