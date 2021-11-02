package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceDataPO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DeviceDataRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(DeviceDataPO deviceDataPO) {
        mongoTemplate.insert(deviceDataPO);
    }

    public void deleteDeviceDataByDevice_Id(Long deviceId) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        mongoTemplate.remove(query, DeviceDataPO.class);
    }

    public void deleteDeviceDataById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, DeviceDataPO.class);
    }

    public void modifyDeviceData(DeviceDataPO deviceDataPO) {
        Query query = new Query(Criteria.where("id").is(deviceDataPO.getId()));

        Update update = new Update();
        update.set("device", deviceDataPO.getDevice());
        update.set("timestamp", deviceDataPO.getTimestamp());
        update.set("data", deviceDataPO.getData());

        mongoTemplate.updateFirst(query, update, DeviceDataPO.class);
    }

    public DeviceDataPO findDeviceDataById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, DeviceDataPO.class);
    }

    public List<DeviceDataPO> findDeviceDataByTimestampBeforeAndTimestampAfter(Date before, Date after) {
        Query query = new Query(Criteria.where("timestamp").gte(after).lte(before));
        return mongoTemplate.find(query, DeviceDataPO.class);
    }

    public List<DeviceDataPO> findDeviceDataByDevice_Id(String deviceId) {
        Query query = new Query(Criteria.where("deviceId").is(deviceId));
        return mongoTemplate.find(query, DeviceDataPO.class);
    }

}
