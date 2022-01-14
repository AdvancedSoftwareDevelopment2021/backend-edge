package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DriverDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.BpmnModelProxy;
import cn.edu.sjtu.ist.ecssbackendedge.utils.process.BpmnUtils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Component
public class Process {

    private String id;

    private String name;

    private String bpmn;

    private int interval;

    private Date createdTime;

    private Step step = Step.BPMN;

    private Status status = Status.CONSTRUCTING;

    private List<String> DeviceList = new ArrayList<>();

    @Autowired
    private DriverDao driverDao;

    public boolean canStart() {
        System.out.println(step);
        System.out.println(status);
        return step == Step.FINISHED && status != Status.RUNNING;
    }

    public void start(Long number) {
        BpmnModelProxy bpmnModelProxy = BpmnModelProxy.fromStream(BpmnUtils.strToInStream(this.bpmn));
        bpmnModelProxy.driverDao = driverDao;
        bpmnModelProxy.startWithKafkaMode(number, interval);
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
