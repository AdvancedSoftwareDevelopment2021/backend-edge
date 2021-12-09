package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.flowNodeImpl;

import cn.edu.sjtu.ist.ecssbackendedge.annotation.FlowNodeProxy;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.History;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.AbstractFlowNodeProxy;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.FlowNodeProxyFactory;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/3 19:59
 */
@Slf4j
@FlowNodeProxy(target = StartEvent.class)
public class StartEventProxy extends AbstractFlowNodeProxy<StartEvent> {

    public StartEventProxy() {
        super(null, null);
    }

    public StartEventProxy(FlowNode node, BpmnModelInstance instance) {
        super((StartEvent) node, instance);
    }

    private AbstractFlowNodeProxy getNextExecuteFlowNode() {
        return FlowNodeProxyFactory.createFlowNodeProxy(node.getSucceedingNodes().singleResult(), instance);
    }

    @Override
    protected List<AbstractFlowNodeProxy> getNextExecuteFlowNodes() {
        return Arrays.asList(getNextExecuteFlowNode());
    }

    @Override
    public void verify() {
        Assert.isTrue(node.getOutgoing().size() == 1, "起点应该只有一个输出");
        Assert.isAssignable(Task.class, node.getSucceedingNodes().singleResult().getClass(), "起点的下一个节点类型应该是Task");
    }

    @Override
    protected void startWithDataCache(Map<String, Map<String, Object>> dataCache, History history) {
        log.info("开始处理BPMN流程");
    }

    @Override
    protected void startWithKafkaMode(String processId) {
        log.info("开始处理基于Kafka模式的BPMN流程");
    }

    @Override
    protected void stop() {
        log.info("开始停止BPMN流程");
    }
}
