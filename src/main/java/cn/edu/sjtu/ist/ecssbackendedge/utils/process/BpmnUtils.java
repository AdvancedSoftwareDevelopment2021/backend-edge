package cn.edu.sjtu.ist.ecssbackendedge.utils.process;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.Element;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.ElementType;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaProperty;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process.ElementType.DEVICE_KEY;

public class BpmnUtils {

    private static String charsetName = "utf-8";

    public static String getExtensionFrom(BaseElement baseElement, String key) {
        ExtensionElements extensionElements = baseElement.getExtensionElements();
        if (extensionElements == null) {
            return null;
        }
        for (CamundaProperty property : extensionElements.getElementsQuery().filterByType(CamundaProperty.class).list()) {
            if (property.getCamundaName().equals(key)) {
                return property.getCamundaValue();
            }
        }
        return null;
    }

    /**
     * 获取不同element上的所有同类型属性
     */
    public static List<Element> getExtensionByType(BpmnModelInstance instance, ElementType type) {
        Collection<? extends FlowElement> elements;
        List<Element> elementDTOS;

        if (type == DEVICE_KEY) {
            elements = instance.getModelElementsByType(Task.class);
            elementDTOS = getExtension(elements, DEVICE_KEY.getKey());
        } else {
            throw new RuntimeException("INCORRECT_BPMN_FORMAT");
//                throw new ServiceException(ResultCode.INCORRECT_BPMN_FORMAT);
        }
        return elementDTOS;
    }

    /**
     * 获取某一element的全部属性
     */
    public static Map<String, String> getExtensionByElement(BpmnModelInstance instance, String elementId) {
        FlowElement element = instance.getModelElementById(elementId);
        if (element == null) {
//            throw new ServiceException(ResultCode.INCORRECT_BPMN_FORMAT);
            throw new RuntimeException("INCORRECT_BPMN_FORMAT");
        }
        Map<String, String> propertyMap = new HashMap<>();

        ExtensionElements extensionElements = element.getExtensionElements();
        if (extensionElements != null) {
            for (CamundaProperty property : extensionElements.getElementsQuery().filterByType(CamundaProperty.class).list()) {
                propertyMap.put(property.getCamundaName(), property.getCamundaValue());
            }
        }
        return propertyMap;
    }

    public static String getExtensionValue(BpmnModelInstance instance, String elementId, String key) {
        ExtensionElements extensionElements = getExtensionElement(instance, elementId);
        if (extensionElements != null) {
            for (CamundaProperty property : extensionElements.getElementsQuery().filterByType(CamundaProperty.class).list()) {
                if (property.getCamundaName().equals(key)) {
                    return property.getCamundaValue();
                }
            }
        }
        return null;
    }

    public static BpmnModelInstance addExtension(BpmnModelInstance instance, String elementId, String key, String value) {
        ExtensionElements extensionElements = getExtensionElement(instance, elementId);
        CamundaProperty property = extensionElements.addExtensionElement(CamundaProperty.class);
        property.setCamundaName(key);
        property.setCamundaValue(value);
        return instance;
    }

    public static BpmnModelInstance setExtension(BpmnModelInstance instance, String elementId, String key, String value) {
        ExtensionElements extensionElements = getExtensionElement(instance, elementId);
        for (CamundaProperty property : extensionElements.getElementsQuery().filterByType(CamundaProperty.class).list()) {
            if (property.getCamundaName().equals(key)) {
                extensionElements.removeChildElement(property);
                break;
            }
        }
        return addExtension(instance, elementId, key, value);
    }

    public static BpmnModelInstance deleteExtension(BpmnModelInstance instance, String elementId, String key){
        ExtensionElements extensionElements = getExtensionElement(instance, elementId);
        for (CamundaProperty property : extensionElements.getElementsQuery().filterByType(CamundaProperty.class).list()) {
            if (property.getCamundaName().equals(key)) {
                extensionElements.removeChildElement(property);
                break;
            }
        }
        return instance;
    }

    private static ExtensionElements getExtensionElement(BpmnModelInstance instance, String elementId) {
        FlowElement element = instance.getModelElementById(elementId);
        if (element == null) {
//            throw new ServiceException(ResultCode.INCORRECT_BPMN_FORMAT);
            throw new RuntimeException("INCORRECT_BPMN_FORMAT");
        }
        ExtensionElements extensionElements = element.getExtensionElements();
        if (extensionElements == null) {
            extensionElements = instance.newInstance(ExtensionElements.class);
            element.setExtensionElements(extensionElements);
        }
        return extensionElements;
    }

    private static List<Element> getExtension(Collection<? extends FlowElement> elements, String key){
        List<Element> elementDTOS = new LinkedList<>();
        boolean isNull;
        for (FlowElement element: elements){
            isNull = true;
            ExtensionElements extensionElements = element.getExtensionElements();
            if (extensionElements != null) {
                for (CamundaProperty property : extensionElements.getElementsQuery().filterByType(CamundaProperty.class).list()) {
                    if (property.getCamundaName().equals(key)){
                        elementDTOS.add(Element.builder()
                                .elementId(element.getId())
                                .elementName(element.getName())
                                .value(property.getCamundaValue())
                                .build());
                        isNull = false;
                        break;
                    }
                }
            }
            if(isNull){
                elementDTOS.add(Element.builder()
                        .elementId(element.getId())
                        .elementName(element.getName())
                        .value(null)
                        .build());
            }

        }
        return elementDTOS;
    }

    public static String bpmnInstToStr(BpmnModelInstance instance) {
        try {
            return Bpmn.convertToString(instance);
        } catch (Exception e) {
            throw new RuntimeException("FILE_CONVERT_ERROR");
//            throw new ServiceException(ResultCode.FILE_CONVERT_ERROR);
        }
    }

    public static String multiFileToStr(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException("FILE_CONVERT_ERROR");
//            throw new ServiceException(ResultCode.FILE_CONVERT_ERROR);
        }
    }

    public static InputStream strToInStream(String str) {
        try {
            return new ByteArrayInputStream(str.getBytes(charsetName));
        } catch (Exception e) {
            throw new RuntimeException("FILE_CONVERT_ERROR");
//            throw new ServiceException(ResultCode.FILE_CONVERT_ERROR);
        }
    }
}
