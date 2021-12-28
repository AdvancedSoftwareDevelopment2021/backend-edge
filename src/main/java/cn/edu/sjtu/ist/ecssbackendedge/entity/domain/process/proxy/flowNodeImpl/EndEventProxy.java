package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.flowNodeImpl;

import cn.edu.sjtu.ist.ecssbackendedge.annotation.FlowNodeProxy;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.History;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.AbstractFlowNodeProxy;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/3 20:15
 */
@Slf4j
@FlowNodeProxy(target = EndEvent.class)
public class EndEventProxy extends AbstractFlowNodeProxy<EndEvent> {

    public EndEventProxy() {
        super(null, null);
    }

    public EndEventProxy(FlowNode node, BpmnModelInstance instance) {
        super((EndEvent)node, instance);
    }

    @Override
    protected List<AbstractFlowNodeProxy> getNextExecuteFlowNodes() {
        return new ArrayList<>(0);
    }

    @Override
    public void verify() {
        if (node.getOutgoing().size() != 0) {
            throw new RuntimeException("INCORRECT_BPMN_FORMAT");
        }
        Assert.isTrue(node.getOutgoing().size() == 0, "终点应该不再有任何输出");
    }

    @Override
    protected void stop() {
        log.info("停止BPMN流程完成");
    }

    @Override
    protected void startWithKafkaMode() {
        log.info("结束BPMN流程");
        try {
            sleep(1500);
        } catch (InterruptedException e) {
            log.info(e.getMessage());
        }
    }

}
