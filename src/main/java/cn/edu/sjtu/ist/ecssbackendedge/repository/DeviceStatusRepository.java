package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceStatusPO;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatusPO, Long> {

    DeviceStatusPO findDeviceStatusById(Long id);

    List<DeviceStatusPO> findDeviceStatusByTimestampBeforeAndTimestampAfter(Date before, Date after);

    List<DeviceStatusPO> findDeviceStatusByDevice_Id(Long deviceId);

    void deleteDeviceStatusByDevice_Id(Long deviceId);

    @Transactional
    void deleteDeviceStatusById(Long id);

}
