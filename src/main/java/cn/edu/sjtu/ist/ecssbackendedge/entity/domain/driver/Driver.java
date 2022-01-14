package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.driver;

import cn.edu.sjtu.ist.ecssbackendedge.dao.*;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.Command;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.CommandData;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command.CommandType;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.device.Device;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.CommandTag;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.machineLearning.Picture;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.Point;

import java.io.*;
import java.util.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

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

    private MLResultDao mlResultDao;

    private PictureDao pictureDao;

    private DeviceDao deviceDao;

    private DriverDao driverDao;

    private List<Command> commands;

    private void updateStatus(DriverStatus status) {
        this.status = status;
        this.driverDao.saveStatus(this.id, this.deviceId, this.status.getStatus());
    }

    // TODO processId
    private List<Command> getExecuteCommands(){
        if(this.commands == null || this.commands.size() <= 0) return null;
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
            commandData.setDeviceId(deviceId);
            try{
                if(command.getCommandType().equals(CommandType.CUSTOM)){
                    commandData.setData(command.getParams().toString());
                    point.executeCustomCommand(id, command.getParams());
                }
                else if(command.getCommandType().equals(CommandType.PROPERTY)){
                    commandData.setData(command.getType() + "/" + command.getValue());
                    point.executePropertyCommand(id, command.getType(), command.getValue());
                }
                else if(command.getCommandType().equals(CommandType.ML)){
//                    File file = point.executeMLCommand(id);
                    File file = new File("C:\\Users\\29472\\Desktop\\test.jpg");
                    Picture picture = new Picture();
//                    picture.setMlModalId(command.getMlId());

                    FileInputStream fileInputStream = new FileInputStream(file);
                    MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                            ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
                    picture.setFile(multipartFile);
                    picture.setTimestamp((new Date()).toString());
                    pictureDao.insertPicture(picture);

                    Double result = this.callPython();
                    Double threshold = command.getThreshold();
                    if(result < threshold){
                        String id = command.getRelatedDeviceId();
                        Device device = deviceDao.findDeviceById(id);
                        if (device != null) {
                            List<Driver> drivers = driverDao.findDriverByDeviceId(device.getId());
                            for( Driver driver : drivers){
                                driver.driverExecuteCommand();
                            }
                        }
                        // TODO 保存机器学习模型的执行结果
                        CommandData data = new CommandData();
                        data.setDeviceId(deviceId);
                        data.setDriverName(name);
                        data.setData(result.toString());
                        data.setStatus(DriverStatus.FAIL);
                        mlResultDao.saveMLResult(data);
                    } else {
                        CommandData data = new CommandData();
                        data.setDeviceId(deviceId);
                        data.setDriverName(name);
                        data.setData(result.toString());
                        data.setStatus(DriverStatus.SUCCESSFUL);
                        mlResultDao.saveMLResult(data);
                    }
                    commandData.setData(command.getMlId());
                } else throw new RuntimeException("指令类型出错");
                commandData.setStatus(DriverStatus.SUCCESSFUL);
            } catch (Exception e){
                e.printStackTrace();
                log.error(e.getMessage());
                commandData.setStatus(DriverStatus.FAIL);
            }finally {
//                commandDataDao.saveCommandData(commandData);
                updateStatus(DriverStatus.SLEEP);
            }

        }
    }

    @Async("ThreadPool")
    public Double callPython() {
        String pythonPath = "python";
        String pythonFile = "C:\\Users\\29472\\Desktop\\nail.py";
        String picPath = "C:\\Users\\29472\\Desktop\\test.jpg";
        String[] cmd = new String[] { pythonPath, pythonFile, picPath};
//        this.broadcastMessage(sId, SolvingTaskStatus.OnSolving);
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(cmd);
            // System.out.println(return_tag);
            // 用输入输出流来截取结果
            System.out.println("Python Execution Output:");
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                Double result = Double.valueOf(line);
                return result;
            }
            in.close();
            int return_tag = proc.waitFor();
            // 异常输出流
            if (return_tag != 0) {
                System.out.println("Python Error Output:");
                in = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
                line = null;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                in.close();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
