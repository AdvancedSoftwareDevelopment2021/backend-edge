package cn.edu.sjtu.ist.ecssbackendedge.utils.compress;

import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDao;
import cn.edu.sjtu.ist.ecssbackendedge.dao.DeviceDataDao;
import cn.edu.sjtu.ist.ecssbackendedge.entity.dto.DeviceDataDTO;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.Device;
import cn.edu.sjtu.ist.ecssbackendedge.model.device.DeviceData;
import cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DeviceDataUtil;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @brief 设备数据打包工具
 * @author rsp
 * @date 2021-11-20
 */
@Slf4j
@Component
public class PackageUtil {

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private DeviceDataDao deviceDataDao;

    @Autowired
    private DeviceDataUtil deviceDataUtil;

    @Value("${device.data.export.path}")
    private String exportPath;

    /**
     * 根据输入的文件名称返回一个完整文件路径
     * @return 完整文件路径
     */
    public String getFilepath(String filename) {
        try {
            File tmp = new File("");
            String filepath = String.format("%s%s%s.xlsx", tmp.getCanonicalPath(), exportPath, filename);
            log.info(String.format("Filepath: %s", filepath));
            return filepath;
        } catch (IOException e) {
            throw new RuntimeException("根据自定义名称返回文件路径失败", e);
        }
    }

    /**
     * 根据时间返回一个完整文件路径
     * @return 完整文件路径
     */
    public String getFilepathByDate() {
        return getFilepath((new Date()).toString());
    }

    /**
     * 保存设备数据
     * @param filepath excel文件的完整路径
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return true/false
     */
    public Boolean packageDeviceHistoryData(String filepath, String startTime, String endTime) {
        OutputStream out;
        ExcelWriter writer;
        try {
            out = new FileOutputStream(filepath);
            writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
        } catch (FileNotFoundException e) {
            log.info(String.format("写入设备历史数据，文件打开失败！文件路径：%s", filepath));
            return false;
        }

        List<Device> deviceList = deviceDao.findAllDevices();
        log.info(String.format("目前设备数量：%d", deviceList.size()));

        for (Device device : deviceList) {
            List<DeviceData> deviceDatas = deviceDataDao.findDeviceAllHistoryDataWithTime(device.getId(), startTime, endTime);
            log.info(String.format("设备id: %s, 查询到的数据量为%d", device.getId(), deviceDatas.size()));

            List<DeviceDataDTO> res = new ArrayList<>();
            for (DeviceData data : deviceDatas) {
                res.add(deviceDataUtil.convertDomain2DTO(data));
            }

            Sheet dataSheet = new Sheet(deviceList.indexOf(device) + 1, 0, DeviceDataDTO.class);
            dataSheet.setSheetName(String.format("%s_%s", device.getId(), device.getName()));
            writer.write(res, dataSheet);
        }
        writer.finish();
        return true;
    }
}

