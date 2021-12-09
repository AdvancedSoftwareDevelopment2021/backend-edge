package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.process;

import lombok.Builder;
import lombok.Data;

/**
 * @author rsp
 */
@Builder
@Data
public class TaskWithDeviceDTO {

    private String taskId;

    private String taskName;

//    private String metadataId;

    private String deviceId;
}
