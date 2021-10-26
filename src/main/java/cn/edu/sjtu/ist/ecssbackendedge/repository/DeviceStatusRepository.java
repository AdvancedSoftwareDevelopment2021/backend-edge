package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface DeviceStatusRepository extends JpaRepository<DeviceStatus, Long> {

    DeviceStatus findDeviceStatusById(Long id);

    List<DeviceStatus> findDeviceStatusByTimestampBeforeAndTimestampAfter(Date before, Date after);

    List<DeviceStatus> findDeviceStatusByDevice_Id(Long deviceId);

    void deleteDeviceStatusByDevice_Id(Long deviceId);

    @Transactional
    void deleteDeviceStatusById(Long id);

}
