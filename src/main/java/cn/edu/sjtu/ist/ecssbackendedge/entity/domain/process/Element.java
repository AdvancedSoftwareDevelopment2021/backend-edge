package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.process;

import lombok.Builder;
import lombok.Data;

/**
 * @author rsp
 */
@Builder
@Data
public class Element {

    private String elementId;

    private String elementName;

    private String value;
}