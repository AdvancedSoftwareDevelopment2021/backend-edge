package cn.edu.sjtu.ist.ecssbackendedge.dao;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.CommandData;
import java.util.List;

/**
 * @author dyanjun
 * @date 2022/1/14 14:22
 */
public interface MLResultDao {

    public boolean saveMLResult(CommandData commandData);

    public List<CommandData> findMLAllHistoryResult(String deviceId, String driverName);

    public List<CommandData> findMLResultWithTime(String deviceId, String driverName, String startTime, String endTime);
}
