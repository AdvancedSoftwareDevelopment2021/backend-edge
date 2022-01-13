package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.driver;

import cn.edu.sjtu.ist.ecssbackendedge.dao.CommandDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DriverDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.Command;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.CommandData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.CommandType;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.CommandTag;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.Point;
import java.util.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

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

    private List<Command> commands;

    private void updateStatus(DriverStatus status) {
        this.status = status;
        this.driverDao.saveStatus(this.id, this.deviceId, this.status.getStatus());
    }

    // TODO processId
    private List<Command> getExecuteCommands(){
        return this.commands.stream()
                .filter(o -> o.getTag() == CommandTag.EXECUTE)
//                .filter(o -> o.getTag() == CommandTag.EXECUTE && o.getProcessId().equals(processId))
                .collect(Collectors.toList());
    }

    // TODO processId
    public void driverExecuteCommand(){
        List<Command> executeCommandList = getExecuteCommands();
        for(Command command: executeCommandList){
            updateStatus(DriverStatus.RUNNING);
            CommandData commandData = new CommandData();
            commandData.setDriverName(name);
            if(command.getCommandType().equals(CommandType.CUSTOM)) {
                commandData.setData(command.getParams().toString());
                try{
                    point.executeCustomCommand(id, command.getParams());
                    commandData.setStatus(DriverStatus.DELIVERED);
                }catch (Exception e){
                    e.printStackTrace();
                    log.error(e.getMessage());
                    commandData.setStatus(DriverStatus.FAIL);
                }finally {
                    commandDataDao.saveCommandData(commandData);
                }
            } else
            if(command.getCommandType().equals(CommandType.PROPERTY)) {
                commandData.setData(command.getType() + "/" + command.getValue());
                try{
                    point.executePropertyCommand(id, command.getType(), command.getValue());
                    commandData.setStatus(DriverStatus.SUCCESSFUL);
                }catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage());
                    commandData.setStatus(DriverStatus.FAIL);
                } finally {
                    commandDataDao.saveCommandData(commandData);
                    updateStatus(DriverStatus.SLEEP);
                }
            } else throw new RuntimeException("指令类型出错");
        }
    }
}
