package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DriverDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.History;
import cn.edu.sjtu.ist.ecssbackendedge.utils.process.BpmnUtils;

import lombok.Data;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @brief AbstractFlowNodeProxy
 * @author rsp
 * @version 0.1
 * @date 2021-11-26
 */
@Data
public abstract class AbstractFlowNodeProxy<T extends FlowNode> {

    final protected T node;

    final protected BpmnModelInstance instance;

    protected boolean running = true;

    public synchronized void setRunning(boolean status) {
        this.running = status;
    };

    public DriverDao driverDao;

    public AbstractFlowNodeProxy(T node, BpmnModelInstance instance) {
        this.node = node;
        this.instance = instance;
    }

    public abstract void verify();

    protected abstract void startWithKafkaMode();

    protected void resume() {}

    protected abstract void stop();

    protected abstract List<AbstractFlowNodeProxy> getNextExecuteFlowNodes();

    List<AbstractFlowNodeProxy> getFollowingFlowNodes() {
        return node.getSucceedingNodes().list()
                .stream()
                .map(x -> FlowNodeProxyFactory.createFlowNodeProxy(x, instance))
                .collect(Collectors.toList());
    }

    protected List<AbstractFlowNodeProxy> getPreviousFlowNode() {
        return node.getPreviousNodes().list()
                .stream()
                .map(x -> FlowNodeProxyFactory.createFlowNodeProxy(x, instance))
                .collect(Collectors.toList());
    }

    String getId() {
        return node.getId();
    }

    protected void addExtension(String key, String value) {
        BpmnUtils.addExtension(instance, node.getId(), key, value);
    }

    protected void setExtension(String key, String value) {
        BpmnUtils.setExtension(instance, node.getId(), key, value);
    }

    protected String getExtension(String key) {
        return BpmnUtils.getExtensionFrom(node, key);
    }

    protected void deleteExtension(String key) {
        BpmnUtils.deleteExtension(instance, node.getId(), key);
    }
}
