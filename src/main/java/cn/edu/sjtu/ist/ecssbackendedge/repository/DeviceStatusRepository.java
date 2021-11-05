package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceStatusPO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DeviceStatusRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(DeviceStatusPO deviceStatusPO) {
        mongoTemplate.insert(deviceStatusPO);
    }

    public void deleteDeviceStatusByDevice_Id(String deviceId) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        mongoTemplate.remove(query, DeviceStatusPO.class);
    }

    public void deleteDeviceStatusById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, DeviceStatusPO.class);
    }

    public void modifyDeviceStatus(DeviceStatusPO deviceStatusPO) {
        Query query = new Query(Criteria.where("id").is(deviceStatusPO.getId()));

        Update update = new Update();
        update.set("device", deviceStatusPO.getDevice());
        update.set("timestamp", deviceStatusPO.getTimestamp());
        update.set("status", deviceStatusPO.getStatus());

        mongoTemplate.updateFirst(query, update, DeviceStatusPO.class);
    }

    public DeviceStatusPO findDeviceStatusById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, DeviceStatusPO.class);
    }

    public List<DeviceStatusPO> findDeviceStatusByTimestampBeforeAndTimestampAfter(Date before, Date after) {
        Query query = new Query(Criteria.where("timestamp").gte(after).lte(before));
        return mongoTemplate.find(query, DeviceStatusPO.class);
    }

    public List<DeviceStatusPO> findDeviceStatusByDevice_Id(String deviceId) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        return mongoTemplate.find(query, DeviceStatusPO.class);
    }

}
