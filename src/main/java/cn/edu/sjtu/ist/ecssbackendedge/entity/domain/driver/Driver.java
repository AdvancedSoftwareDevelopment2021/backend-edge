package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.driver;

import cn.edu.sjtu.ist.ecssbackendedge.dao.CommandDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DriverDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.CommandData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.CommandType;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.Point;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author dyanjun
 * @date 2021/12/26 0:03
 */
@Data
@Slf4j
public class Driver {

    private String id;

    private String deviceId;

    private String name;

    private DriverStatus status = DriverStatus.SLEEP;

    private Point point;

    private CommandDataDao commandDataDao;

    private DriverDao driverDao;

    private void updateStatus(DriverStatus status) {
        this.status = status;
        this.driverDao.saveStatus(this.id, this.deviceId, this.status.getStatus());
    }

    public void executeCommand(CommandType commandType, Boolean asy, String type, String value, Map<String, Object> params){
        updateStatus(DriverStatus.RUNNING);
        CommandData commandData = new CommandData();
        commandData.setDeviceId(deviceId);
        commandData.setDriverName(name);
        if(commandType.equals(CommandType.CUSTOM)) {
            commandData.setData(params.toString());
                try{
                    point.executeCustomCommand(id, params);
                    if(asy){
                        commandData.setStatus(DriverStatus.DELIVERED);
                    }else {
                        commandData.setStatus(DriverStatus.SUCCESSFUL);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    log.error(e.getMessage());
                    commandData.setStatus(DriverStatus.FAIL);
                }finally {
                    commandDataDao.saveCommandData(commandData);
                }
            } else
            if(commandType.equals(CommandType.PROPERTY)) {
                commandData.setData(type + "/" + value);
                try{
                    point.executePropertyCommand(id, type, value);
                    if(asy){
                        commandData.setStatus(DriverStatus.DELIVERED);
                    }else {
                        commandData.setStatus(DriverStatus.SUCCESSFUL);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    commandData.setStatus(DriverStatus.FAIL);
                } finally {
                    commandDataDao.saveCommandData(commandData);
                }
            } else throw new RuntimeException("指令类型出错");
        updateStatus(DriverStatus.SLEEP);
    }
}
