package cn.edu.sjtu.ist.ecssbackendedge.utils.convert;

import cn.edu.sjtu.ist.ecssbackendedge.dao.CommandDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DriverDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.driver.Driver;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.driver.DriverDTO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.driver.DriverPO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dyanjun
 * @date 2021/12/26 0:19
 */
@Component
public class DriverUtil {
    @Autowired
    PointUtil pointUtil;

    @Autowired
    CommandDataDao commandDataDao;

    @Autowired
    DriverDao driverDao;

    public Driver convertDTO2Domain(DriverDTO dto) {
        Driver res = new Driver();
        BeanUtils.copyProperties(dto, res);
        res.setCommandDataDao(commandDataDao);
        res.setDriverDao(driverDao);
        return res;
    }

    public DriverDTO convertDomain2DTO(Driver domain) {
        DriverDTO res = new DriverDTO();
        BeanUtils.copyProperties(domain, res);
        return res;
    }

    public Driver convertPO2Domain(DriverPO po) {
        Driver res = new Driver();
        BeanUtils.copyProperties(po, res);
        res.setPoint(pointUtil.convertPO2Domain(po.getPointPO()));
        res.setCommandDataDao(commandDataDao);
        res.setDriverDao(driverDao);
        return res;
    }

    public DriverPO convertDomain2PO(Driver domain) {
        DriverPO res = new DriverPO();
        BeanUtils.copyProperties(domain, res);
        res.setPointPO(pointUtil.convertDomain2PO(domain.getPoint()));
        return res;
    }
}
