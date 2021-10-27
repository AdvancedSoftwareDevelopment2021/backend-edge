package cn.edu.sjtu.ist.ecssbackendedge.repository.modbus;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.modbus.ModbusCollector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dyanjun
 * @date 2021/10/27 20:58
 */
@Repository
public interface ModbusCollectorRepository extends JpaRepository<ModbusCollector, Long> {
    ModbusCollector findModbusCollectorById(String id);

    void deleteById(String id);

    List<ModbusCollector> findModbusCollectorsByName(String name);

    List<ModbusCollector> findModbusCollectorsByIp(String ip);

    List<ModbusCollector> findModbusCollectorsByIpAndPort(String ip, Integer port);

    List<ModbusCollector> findModbusCollectorsByIpType(Integer ipType);

}
