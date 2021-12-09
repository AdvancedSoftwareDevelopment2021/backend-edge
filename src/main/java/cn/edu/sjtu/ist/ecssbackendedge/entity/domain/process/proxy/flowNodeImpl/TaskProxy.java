package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.flowNodeImpl;

import cn.edu.sjtu.ist.ecssbackendedge.annotation.FlowNodeProxy;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.ElementType;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.History;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.AbstractFlowNodeProxy;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.FlowNodeProxyFactory;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.JsonUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.ItemAwareElement;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/3 20:18
 */
@Slf4j
@FlowNodeProxy(target = Task.class)
public class TaskProxy extends AbstractFlowNodeProxy<Task> {

    final static private String TARGET_SERVICE_KEY = "targetDevice";

    final static private String DETECT_SERVICE_KEY = "detectDevice";

    final static private String INPUT_KEY = "input";

    /**
     * 流程Id
     */
    private String processId;

    /**
     * Task目标设备
     */
    private String deviceId;

    public TaskProxy() {
        super(null, null);
    }

    public TaskProxy(FlowNode node, BpmnModelInstance instance) {
        super((Task) node, instance);
        this.deviceId = getExtension(ElementType.DEVICE_KEY.getKey());
    }

    @Override
    public void verify() {
        Assert.isTrue(node.getOutgoing().size() == 1, "Task节点的输出应该只有一个");
        Assert.notNull(this.deviceId, "Task节点的目标设备应该被绑定");
    }

    @Override
    protected List<AbstractFlowNodeProxy> getNextExecuteFlowNodes() {
        AbstractFlowNodeProxy res = FlowNodeProxyFactory.createFlowNodeProxy(node.getSucceedingNodes().singleResult(), instance);
        return Arrays.asList(res);
    }

    @Override
    protected void startWithDataCache(Map<String, Map<String, Object>> dataCache, History history) {
        log.info("startWithDataCache, 处理Task节点: {}", node.getId());
//        String taskId = node.getId();
//        Service targetService = JsonUtil.readValues(getExtension(TARGET_SERVICE_KEY), Service.class);
//        // FIXME 去掉processId,taskId,status,data等的硬编码
//        // TODO 加入log记录
//        Map<String, Object> processInformation = new HashMap<>(2);
//        processInformation.put("processId", history.getId());
//        processInformation.put("taskId", taskId);
//        Map<String, Object> input = getInput(dataCache);
//        input.putAll(processInformation);
//        targetService.invoke(input);
//        Service detectService = JsonUtil.readValues(getExtension(DETECT_SERVICE_KEY), Service.class);
//        while (true) {
//            Map<String, Object> res = detectService.invoke(processInformation);
//            if (res.get("status") != null && (int) res.get("status") == TaskStatus.DONE.ordinal()) {
//                dataCache.put(taskId, (Map<String, Object>) res.get("data"));
//                break;
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                log.error("", e);
//            }
//        }
    }

    private Map<String, Object> getInput(Map<String, Map<String, Object>> dataCache) {
        Map<String, Object> res = new HashMap<>(0);
        String inputRaw = getExtension(INPUT_KEY);
        if (!StringUtils.isEmpty(inputRaw)) {
            Map<String, String> input = JsonUtil.readValues(inputRaw, Map.class);
            for (HashMap.Entry<String, String> entry : input.entrySet()) {
                String key = entry.getKey();
                // value is like ${taskId}.${outputValue}
                String value = entry.getValue();
                String inputTaskId = value.split("[.]")[0];
                String inputTaskOutput = value.split("[.]")[1];
                res.put(key, dataCache.get(inputTaskId).get(inputTaskOutput));
            }
        }
        return res;
    }

    @Override
    protected void startWithKafkaMode(String processId) {
        log.info("处理Task节点: {}", node.getId());
        this.processId = processId;
    }

    private ItemAwareElement getItemAwareElement(Collection<ItemAwareElement> list) {
        return list.iterator().next();
    }

    @Override
    protected void stop() {
    }

    @Override
    protected void resume() {
    }
}
