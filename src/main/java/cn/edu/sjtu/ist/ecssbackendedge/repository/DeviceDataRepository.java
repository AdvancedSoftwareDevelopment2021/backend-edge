package cn.edu.sjtu.ist.ecssbackendedge.repository;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceDataPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface DeviceDataRepository extends JpaRepository<DeviceDataPO, Long> {

    DeviceDataPO findDeviceDataById(Long id);

    List<DeviceDataPO> findDeviceDataByTimestampBeforeAndTimestampAfter(Date before, Date after);

    List<DeviceDataPO> findDeviceDataByDevice_Id(Long deviceId);

    void deleteDeviceDataByDevice_Id(Long deviceId);

    @Transactional
    void deleteDeviceDataById(Long id);

}
