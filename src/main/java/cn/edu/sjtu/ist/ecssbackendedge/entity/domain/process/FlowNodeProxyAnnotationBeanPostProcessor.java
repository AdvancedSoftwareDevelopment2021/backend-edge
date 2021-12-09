package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process;

import cn.edu.sjtu.ist.ecssbackendedge.annotation.FlowNodeProxy;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.proxy.FlowNodeProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

/**
 * @Author: ssingualrity
 * @Date: 2020/4/8 14:36
 */
@Component
@Slf4j
public class FlowNodeProxyAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        FlowNodeProxy annotation = bean.getClass().getAnnotation(FlowNodeProxy.class);
        if (annotation != null) {
            try {
                Constructor constructor = bean.getClass().getConstructor(FlowNode.class, BpmnModelInstance.class);
                FlowNodeProxyFactory.register(annotation.target(), constructor);
                log.info("{} 已经注册", bean.getClass());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
