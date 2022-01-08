package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.driver;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.Command;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.driver.DriverStatus;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.Point;
import lombok.Data;
import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/26 16:18
 */
@Data
public class DriverDTO {

    private String id;

    private String deviceId;

    private String name;

    private DriverStatus status = DriverStatus.SLEEP;

    private Point point;

    private List<Command> commands;
}
