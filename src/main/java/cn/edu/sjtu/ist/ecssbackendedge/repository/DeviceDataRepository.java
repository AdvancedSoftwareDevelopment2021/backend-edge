package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface DeviceDataRepository extends JpaRepository<DeviceData, Long> {

    DeviceData findDeviceDataById(Long id);

    List<DeviceData> findDeviceDataByTimestampBeforeAndTimestampAfter(Date before, Date after);

    List<DeviceData> findDeviceDataByDevice_Id(Long deviceId);

    void deleteDeviceDataByDevice_Id(Long deviceId);

    @Transactional
    void deleteDeviceDataById(Long id);

}
