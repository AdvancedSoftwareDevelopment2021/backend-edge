package cn.edu.sjtu.ist.ecssbackendedge.model.process.proxy;

import cn.edu.sjtu.ist.ecssbackendedge.model.process.History;
import cn.edu.sjtu.ist.ecssbackendedge.utils.process.BpmnUtils;
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
public abstract class AbstractFlowNodeProxy<T extends FlowNode> {
    final protected T node;

    final protected BpmnModelInstance instance;

    public AbstractFlowNodeProxy(T node, BpmnModelInstance instance) {
        this.node = node;
        this.instance = instance;
    }

    public abstract void verify();

    protected abstract List<AbstractFlowNodeProxy> getNextExecuteFlowNodes();

    protected abstract void startWithDataCache(Map<String, Map<String, Object>> dataCache, History history);

    protected abstract void startWithKafkaMode(String processId);

    protected void resume() {

    }

    protected abstract void stop();

    /**
     * 获取对应的隐式数据源Id，从而完成Channel到DataSource的注册，打通流程
     * @return 隐式数据源Id
     */
    public abstract String getDataSourceId();

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
