package cn.edu.sjtu.ist.ecssbackendedge.utils.convert;

import cn.edu.sjtu.ist.ecssbackendedge.entity.po.EdgePO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.Edge;
import cn.edu.sjtu.ist.ecssbackendedge.utils.connect.ConnectCloudUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EdgeUtil {

    @Autowired
    private CollectSchedulerUtil collectSchedulerUtil;

    // public Edge convertDTO2Domain(EdgeDTO dto) {
    //     Edge res = new Edge();
    //     res.setId(dto.getId());
    //     res.setName(dto.getName());
    //     res.setModel(dto.getModel());
    //     res.setDescription(dto.getDescription());
    //     res.setValues(dto.getValues());
    //     return res;
    // }

    // public EdgeDTO convertDomain2DTO(Edge domain) {
    //     EdgeDTO res = new EdgeDTO();
    //     res.setId(domain.getId());
    //     res.setName(domain.getName());
    //     res.setModel(domain.getModel());
    //     res.setDescription(domain.getDescription());
    //     res.setValues(domain.getValues());
    //     return res;
    // }

    public Edge convertPO2Domain(EdgePO po) {
        Edge res = new Edge();
        res.setId(po.getId());
        res.setCollectorScheduler(collectSchedulerUtil.convertPO2Domain(po.getCollectorScheduler()));
        res.setLastTime(po.getCreatedTime());
        return res;
    }

    public EdgePO convertDomain2PO(Edge domain) {
        EdgePO res = new EdgePO();
        res.setId(domain.getId());
        res.setCloudUrl(ConnectCloudUtil.CLOUD_SERVER_URL);
        res.setCollectorScheduler(collectSchedulerUtil.convertDomain2PO(domain.getCollectorScheduler()));
        res.setCreatedTime(domain.getLastTime());
        return res;
    }
}
