package cn.edu.sjtu.ist.ecssbackendedge.dao.impl;

import cn.edu.sjtu.ist.ecssbackendedge.dao.EdgeDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.EdgePO;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.Edge;
import cn.edu.sjtu.ist.ecssbackendedge.repository.EdgeRepository;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.EdgeUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EdgeDaoImpl implements EdgeDao {

    @Autowired
    private EdgeRepository edgeRepository;

    @Autowired
    private EdgeUtil edgeUtil;

    @Override
    public void insertAndUpdate(Edge edge) {
        edgeRepository.deleteAll();
        EdgePO po = edgeUtil.convertDomain2PO(edge);
        log.info(String.format("当前edge信息：%s", po));
        edgeRepository.save(po);
    }
}
