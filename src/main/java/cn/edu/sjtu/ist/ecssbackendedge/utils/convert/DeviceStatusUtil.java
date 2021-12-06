package cn.edu.sjtu.ist.ecssbackendedge.utils.convert;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceStatusDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceStatusPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceStatus;
import org.springframework.stereotype.Component;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备状态对象转换工具类
 * @date 2021-11-20
 */
@Component
public class DeviceStatusUtil {

    public DeviceStatus convertDTO2Domain(DeviceStatusDTO dto) {
        DeviceStatus res = new DeviceStatus();
        res.setId(dto.getId());
        res.setDeviceId(dto.getDeviceId());
        res.setSensorName(dto.getSensorName());
        res.setTimestamp(dto.getTimestamp());
        res.setStatus(dto.getStatus());
        return res;
    }

    public DeviceStatusDTO convertDomain2DTO(DeviceStatus domain) {
        DeviceStatusDTO res = new DeviceStatusDTO();
        res.setId(domain.getId());
        res.setDeviceId(domain.getDeviceId());
        res.setSensorName(domain.getSensorName());
        res.setTimestamp(domain.getTimestamp());
        res.setStatus(domain.getStatus());
        return res;
    }

    public DeviceStatus convertPO2Domain(DeviceStatusPO po) {
        DeviceStatus res = new DeviceStatus();
        res.setId(po.getId());
        res.setDeviceId(po.getDeviceId());
        res.setSensorName(po.getSensorName());
        res.setTimestamp(po.getTimestamp());
        res.setStatus(po.getStatus());
        return res;
    }

    public DeviceStatusPO convertDomain2PO(DeviceStatus domain) {
        DeviceStatusPO res = new DeviceStatusPO();
        res.setId(domain.getId());
        res.setDeviceId(domain.getDeviceId());
        res.setSensorName(domain.getSensorName());
        res.setTimestamp(domain.getTimestamp());
        res.setStatus(domain.getStatus());
        return res;
    }
}
