package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command;

import lombok.Data;
import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/25 21:41
 */
@Data
public class Command {
    String id;

    String name;

    String description;

    CommandType type;

    String sensorId;

    String controllerId;

    Map<String, String> content;

    CommandStatus status;
}
