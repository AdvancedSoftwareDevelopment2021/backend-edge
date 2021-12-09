package cn.edu.sjtu.ist.ecssbackendedge.annotation;

import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/8 14:34
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface FlowNodeProxy {
    Class<? extends FlowNode> target();
}
