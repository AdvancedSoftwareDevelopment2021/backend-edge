package cn.edu.sjtu.ist.ecssbackendedge.model.process.proxy;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FlowNodeProxyFactory {

    private static Map<Class<? extends FlowNode>, Constructor<? extends AbstractFlowNodeProxy>> registry = new HashMap<>();

    public static void register(Class<? extends FlowNode> clazz, Constructor<? extends AbstractFlowNodeProxy> constructor) {
        registry.put(clazz, constructor);
    }

    public static AbstractFlowNodeProxy createFlowNodeProxy(FlowNode flowNode, BpmnModelInstance instance) {
        Class<? extends FlowNode> clazz = flowNode.getClass();
        for (Map.Entry<Class<? extends FlowNode>, Constructor<? extends AbstractFlowNodeProxy>> entry : registry.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz)) {
                try {
                    return entry.getValue().newInstance(flowNode, instance);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
        throw new RuntimeException("INCORRECT_BPMN_FORMAT");
    }
}
