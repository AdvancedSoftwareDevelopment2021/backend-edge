package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DriverDao;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.*;

@Slf4j
public class BpmnModelProxy {

    private Visitor visitor;

    private BpmnModelInstance instance;

    public DriverDao driverDao;

    public static BpmnModelProxy fromStream(InputStream inputStream) {
        BpmnModelInstance instance = Bpmn.readModelFromStream(inputStream);
        BpmnModelProxy res = new BpmnModelProxy();
        res.instance = instance;
        return res;
    }

    public void verifySelf() {
        Assert.isTrue(instance.getModelElementsByType(StartEvent.class).size() == 1, "BPMN模型应该只有一个起点");
        Assert.isTrue(instance.getModelElementsByType(EndEvent.class).size() == 1, "BPMN模型应该只有一个终点");
    }

    public void startWithKafkaMode(Long number) {
        // 遍历bpmn，将所有节点加入到Visitor中
        this.visitor = new Visitor();
        this.visitor.driverDao = this.driverDao;
        findAllBpmnNodes();
        try {
            AbstractFlowNodeProxy startEvent = getStartEvent();
            for (int i = 0; i < number; i++) {
                visitor.addNumber(startEvent.getId());
            }
        }
        catch (Exception e) {
            log.error("", e);
            throw new RuntimeException("BPMN_PROCESS_EXECUTE_ERROR");
        }
    }

    public void stop() {
    }

    private void findAllBpmnNodes() {
        Set<String> parsedNode = new HashSet<>();
        final Queue<AbstractFlowNodeProxy> queue = new LinkedList<>();
        queue.add(getStartEvent());
        while (!queue.isEmpty()) {
            AbstractFlowNodeProxy node = queue.poll();
            if (!parsedNode.contains(node.getId())) {
                this.visitor.addNode(node);
                parsedNode.add(node.getId());
                for (Object object : node.getFollowingFlowNodes()) {
                    AbstractFlowNodeProxy followingNode = (AbstractFlowNodeProxy) object;
                    queue.add(followingNode);
                }
            }
        }
    }

    AbstractFlowNodeProxy getStartEvent() {
        StartEvent startEvent = instance.getModelElementsByType(StartEvent.class).iterator().next();
        return FlowNodeProxyFactory.createFlowNodeProxy(startEvent, instance);
    }

    @Override
    public String toString() {
        return Bpmn.convertToString(instance);
    }
}