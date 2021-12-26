package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.CommandData;
import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/26 23:15
 */
public interface CommandDataDao {
    boolean saveCommandData(CommandData commandData);

    List<CommandData> findDriverAllHistoryData(String deviceId, String driverName);
}
