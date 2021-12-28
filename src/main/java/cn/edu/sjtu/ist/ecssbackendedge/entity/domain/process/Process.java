package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.BpmnModelProxy;
import cn.edu.sjtu.ist.ecssbackendedge.utils.process.BpmnUtils;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Process {

    private String id;

    private String name;

    private String bpmn;

    private Date createdTime;

    private Step step = Step.BPMN;

    private Status status = Status.CONSTRUCTING;

    private List<String> DeviceList = new ArrayList<>();

    public boolean canStart() {
        return step == Step.FINISHED && status != Status.RUNNING;
    }

    public void start(Long number) {
        BpmnModelProxy bpmnModelProxy = BpmnModelProxy.fromStream(BpmnUtils.strToInStream(this.bpmn));
        bpmnModelProxy.startWithKafkaMode(number);
        this.bpmn = bpmnModelProxy.toString();
        this.status = Status.RUNNING;
    }

    public boolean canStop() {
        return status == Status.RUNNING;
    }

    public void stop() {
        BpmnModelProxy bpmnModelProxy = BpmnModelProxy.fromStream(BpmnUtils.strToInStream(this.bpmn));
        bpmnModelProxy.stop();
        this.bpmn = bpmnModelProxy.toString();
        this.status = Status.STOPPED;
    }

    public void verifySelf() {
        BpmnModelProxy bpmnModelProxy = BpmnModelProxy.fromStream(BpmnUtils.strToInStream(this.bpmn));
        bpmnModelProxy.verifySelf();
    }
}
