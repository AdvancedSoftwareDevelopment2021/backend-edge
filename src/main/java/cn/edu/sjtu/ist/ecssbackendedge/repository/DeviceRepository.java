package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DevicePO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<DevicePO, Long> {

    DevicePO findDeviceById(Long id);

    void deleteById(Long id);

    List<DevicePO> findDevicesByName(String name);
}
