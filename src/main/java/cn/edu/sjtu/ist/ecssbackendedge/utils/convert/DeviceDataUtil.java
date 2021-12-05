package cn.edu.sjtu.ist.ecssbackendedge.utils.convert;

import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDataDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.DeviceDataPO;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceData;
import org.springframework.stereotype.Component;

/**
 * @author rsp
 * @version 0.1
 * @brief 设备数据对象转换工具类
 * @date 2021-11-20
 */
@Component
public class DeviceDataUtil {

    public DeviceData convertDTO2Domain(DeviceDataDTO dto) {
        DeviceData res = new DeviceData();
        res.setDeviceId(dto.getDeviceId());
        res.setSensorName(dto.getSensorName());
        res.setTimestamp(dto.getTimestamp());
        res.setData(dto.getData());
        return res;
    }

    public DeviceDataDTO convertDomain2DTO(DeviceData domain) {
        DeviceDataDTO res = new DeviceDataDTO();
        res.setDeviceId(domain.getDeviceId());
        res.setSensorName(domain.getSensorName());
        res.setTimestamp(domain.getTimestamp());
        res.setData(domain.getData());
        return res;
    }

    public DeviceData convertPO2Domain(DeviceDataPO po) {
        DeviceData res = new DeviceData();
        res.setId(po.getId());
        res.setDeviceId(po.getDeviceId());
        res.setSensorName(po.getSensorName());
        res.setTimestamp(po.getTimestamp());
        res.setData(po.getData());
        return res;
    }

    public DeviceDataPO convertDomain2PO(DeviceData domain) {
        DeviceDataPO res = new DeviceDataPO();
        res.setId(domain.getId());
        res.setDeviceId(domain.getDeviceId());
        res.setSensorName(domain.getSensorName());
        res.setTimestamp(domain.getTimestamp());
        res.setData(domain.getData());
        return res;
    }
}
