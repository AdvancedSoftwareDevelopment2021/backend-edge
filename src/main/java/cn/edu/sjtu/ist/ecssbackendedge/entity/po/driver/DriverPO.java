package cn.edu.sjtu.ist.ecssbackendedge.entity.po.driver;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.Command;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.driver.DriverStatus;
import cn.edu.sjtu.ist.ecssbackendedge.entity.po.point.PointPO;
import lombok.Data;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/26 0:18
 */
@Data
@Profile("mongodb")
@Document(collection = "driver")
public class DriverPO {
    @Id
    private String id;

    @Field
    private String deviceId;

    @Field
    private String name;

    @Field
    private DriverStatus status = DriverStatus.SLEEP;

    @Field
    private PointPO pointPO;

    @Field
    private List<Command> commands;
}
