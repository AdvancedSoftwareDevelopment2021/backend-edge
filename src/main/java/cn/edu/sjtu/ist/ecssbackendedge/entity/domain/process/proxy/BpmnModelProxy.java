package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.History;

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

    private interface Visitor {
        void onVisit(AbstractFlowNodeProxy node);
    }

    public static BpmnModelProxy fromStream(InputStream inputStream) {
        BpmnModelInstance instance = Bpmn.readModelFromStream(inputStream);
        BpmnModelProxy res = new BpmnModelProxy();
        res.instance = instance;
        return res;
    }

    private BpmnModelInstance instance;

    public void verifySelf() {
        Assert.isTrue(instance.getModelElementsByType(StartEvent.class).size() == 1, "BPMN模型应该只有一个起点");
        Assert.isTrue(instance.getModelElementsByType(EndEvent.class).size() == 1, "BPMN模型应该只有一个终点");
        walkThrough(AbstractFlowNodeProxy::verify);
    }

    public void startWithPollingMode(History history) {
        Map<String, Map<String, Object>> dataCache = new HashMap<>(8);
        Queue<AbstractFlowNodeProxy> queue = new LinkedList<>();
        queue.add(getStartEvent());
        while (!queue.isEmpty()) {
            AbstractFlowNodeProxy node = queue.poll();
            node.startWithDataCache(dataCache, history);
            queue.addAll(node.getNextExecuteFlowNodes());
        }
    }

    public void startWithKafkaMode(String processId) {
        try {
            walkThrough(x -> x.startWithKafkaMode(processId));
        }
        catch (Exception e) {
            log.error("", e);
            walkThrough(AbstractFlowNodeProxy::resume);
            throw new RuntimeException("BPMN_PROCESS_EXECUTE_ERROR");
//            throw new ServiceException(BPMN_PROCESS_EXECUTE_ERROR);
        }
    }

    public void stop() {
        walkThrough(AbstractFlowNodeProxy::stop);
    }

    private void walkThrough(Visitor visitor) {
        Set<String> parsedNode = new HashSet<>();
        final Queue<AbstractFlowNodeProxy> queue = new LinkedList<>();
        queue.add(getStartEvent());
        while (!queue.isEmpty()) {
            AbstractFlowNodeProxy node = queue.poll();
            if (!parsedNode.contains(node.getId())) {
                visitor.onVisit(node);
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