package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.flowNodeImpl;

import cn.edu.sjtu.ist.ecssbackendedge.annotation.FlowNodeProxy;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.ElementType;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.History;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.AbstractFlowNodeProxy;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.FlowNodeProxyFactory;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.ParallelGateway;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ssingularity
 */
@Slf4j
@FlowNodeProxy(target = ParallelGateway.class)
public class ParallelGatewayProxy extends AbstractFlowNodeProxy<ParallelGateway> {

    public ParallelGatewayProxy() {
        super(null, null);
    }

    public ParallelGatewayProxy(FlowNode node, BpmnModelInstance instance) {
        super((ParallelGateway)node, instance);
    }

    private List<AbstractFlowNodeProxy> nextNodeProxy = new ArrayList<>();

    @Override
    public void verify() {
        Assert.isTrue(node.getIncoming().size() == 1, "并行网关的输入应该只有一个");
        Assert.isAssignable(Task.class, node.getIncoming().iterator().next().getSource().getClass(), "并行网关的输入类型应该为Task");
        Assert.isTrue(node.getOutgoing().size() >= 1, "并行网关的输出应该至少有一个");
        for (SequenceFlow sequenceFlow : node.getOutgoing()) {
            Assert.isAssignable(Task.class, sequenceFlow.getTarget().getClass(), "并行网关的输出类型应该为Task");
        }
    }

    @Override
    public List<AbstractFlowNodeProxy> getNextExecuteFlowNodes() {
        return nextNodeProxy;
    }

    @Override
    protected void startWithKafkaMode(int interval) {
        log.info("处理并行网关节点: {}", node.getId());
    }

    @Override
    protected void stop() {
    }

    @Override
    protected void resume() {
    }
}
