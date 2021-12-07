package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.process;

import lombok.Builder;
import lombok.Data;

/**
 * @author rsp
 */
@Builder
@Data
public class ElementDTO {

    private String elementId;

    private String elementName;

    private String value;
}