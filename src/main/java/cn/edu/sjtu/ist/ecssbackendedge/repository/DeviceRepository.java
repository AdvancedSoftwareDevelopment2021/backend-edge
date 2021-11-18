package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DevicePO;

import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

@Component
public class DeviceRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(DevicePO devicePO) {
        mongoTemplate.insert(devicePO);
    }

    public void deleteById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, DevicePO.class);
    }

    public void modifyDevice(DevicePO devicePO) {
        Query query = new Query(Criteria.where("id").is(devicePO.getId()));

        Update update = new Update();
        update.set("name", devicePO.getName());
        update.set("model", devicePO.getModel());

        mongoTemplate.updateFirst(query, update, DevicePO.class);
    }

    public List<DevicePO>  findDeviceAll() {
        return mongoTemplate.findAll(DevicePO.class);
    }

    public DevicePO findDeviceById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, DevicePO.class);
    }

    public List<DevicePO> findDevicesByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.find(query, DevicePO.class);
    }
}
