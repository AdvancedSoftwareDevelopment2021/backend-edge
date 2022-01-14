package cn.edu.sjtu.ist.ecssbackendedge.utils.point.zigbee;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.sensor.Sensor;

import gnu.io.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;

/**
 * @author dyanjun
 * @date 2021/11/21 16:51
 */
@Slf4j
public class ZigBeeListener implements SerialPortEventListener {

    // 检测系统中可用的通讯端口类
    private CommPortIdentifier commPortId;
    // 枚举类型
    private Enumeration<CommPortIdentifier> portList;
    // RS232串口
    private SerialPort serialPort;
    // 输入流
    private InputStream inputStream;
    // 输出流
    private OutputStream outputStream;
    // 保存串口返回信息
    private String data;
    // 保存串口返回信息十六进制
    private String dataHex;

    private Sensor sensor;

    public ZigBeeListener() {
    }

    public ZigBeeListener(Sensor sensor) {
        this.sensor = sensor;
    }

    /**
     * Hex字符串转byte
     */
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * hex字符串转byte数组
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            // 奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            // 偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * 数组转换成十六进制字符串
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }


    public static int hexToDec(String hex) {
        int dec = 0;
        int upper = hex.length();
        for (int i = 0; i < hex.length(); i++) {
            if (hex.charAt(i) == 'A') {
                dec += 10 * Math.pow(16, upper);
            } else if (hex.charAt(i) == 'B') {
                dec += 11 * Math.pow(16, upper);
            } else if (hex.charAt(i) == 'C') {
                dec += 12 * Math.pow(16, upper);
            } else if (hex.charAt(i) == 'D') {
                dec += 13 * Math.pow(16, upper);
            } else if (hex.charAt(i) == 'E') {
                dec += 14 * Math.pow(16, upper);
            } else if (hex.charAt(i) == 'F') {
                dec += 15 * Math.pow(16, upper);
            } else {
                int b = hex.charAt(i);
                int n = b - (int) '0';
                dec += n * Math.pow(16, upper);
            }
            upper--;
        }
        return dec;
    }


    /**
     * 初始化串口
     */
    @SuppressWarnings("unchecked")
    public void init(String serialNumber, int baudRate, int checkoutBit, int dataBit, int stopBit) {
        // 获取系统中所有的通讯端口
        portList = CommPortIdentifier.getPortIdentifiers();
        // 记录是否含有指定串口
        boolean isExsist = false;
        // 循环通讯端口
        while (portList.hasMoreElements()) {
            commPortId = portList.nextElement();
            // 判断是否是串口
            if (commPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                // 比较串口名称是否是指定串口
                if (serialNumber.equals(commPortId.getName())) {
                    // 串口存在
                    isExsist = true;
                    // 打开串口
                    try {
                        serialPort = (SerialPort) commPortId.open(Object.class.getSimpleName(), 2000);
                        // 设置串口监听
                        serialPort.addEventListener(this);
                        // 设置串口数据时间有效(可监听)
                        serialPort.notifyOnDataAvailable(true);
                        // 设置串口通讯参数:波特率，数据位，停止位,校验方式
                        serialPort.setSerialPortParams(baudRate, dataBit,
                                stopBit, checkoutBit);
                    } catch (PortInUseException e) {
                        log.info("端口被占用");
                    } catch (TooManyListenersException e) {
                        log.info("监听器过多");
                    } catch (UnsupportedCommOperationException e) {
                        log.info("不支持的COMM端口操作异常");
                    }
                    // 结束循环
                    break;
                }
            }
        }
        // 若不存在该串口则抛出异常
        if (!isExsist) {
            log.info("不存在该串口");
        }
    }

    /**
     * 实现接口SerialPortEventListener中的方法 读取从串口中接收的数据
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.BI: // 通讯中断
            case SerialPortEvent.OE: // 溢位错误
            case SerialPortEvent.FE: // 帧错误
            case SerialPortEvent.PE: // 奇偶校验错误
            case SerialPortEvent.CD: // 载波检测
            case SerialPortEvent.CTS: // 清除发送
            case SerialPortEvent.DSR: // 数据设备准备好
            case SerialPortEvent.RI: // 响铃侦测
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 输出缓冲区已清空
                break;
            case SerialPortEvent.DATA_AVAILABLE: // 有数据到达
                // 调用读取数据的方法
                readComm();
                break;
            default:
                break;
        }
    }

    /**
     * 读取串口返回信息
     */
    public void readComm() {
        try {
            inputStream = serialPort.getInputStream();
            // 通过输入流对象的available方法获取数组字节长度
            byte[] readBuffer = new byte[inputStream.available()];
            // 从线路上读取数据流
            int len = 0;
            while ((len = inputStream.read(readBuffer)) != -1) { // 直接获取到的数据
                data = new String(readBuffer, 0, len).trim();
                inputStream.close();
                inputStream = null;
                // 保存数据
                log.info("zigbee保存数据：" + sensor.getName() + ": " + data);
                if (data != null && !data.equals("null")) {
                    // TODO 保存数据的方式有待商榷
                    sensor.getDeviceDataDao().saveDeviceData(sensor.getDeviceId(), sensor.getName(), data);
                }
                sensor.getSensorDao().saveSensorStatus(sensor.getDeviceId(), sensor.getName(), sensor.getStatus().getType());
                return;
            }
        } catch (IOException e) {
            log.info("读取串口数据时发生IO异常");
        }
    }

    /**
     * 发送信息到串口
     */
    public void sendComm(String data) {
        byte[] writerBuffer = null;
        try {
            writerBuffer = data.getBytes();
        } catch (NumberFormatException e) {
            log.info("命令格式错误");
        }
        try {
            outputStream = serialPort.getOutputStream();
            assert writerBuffer != null;
            outputStream.write(writerBuffer);
            outputStream.flush();
        } catch (NullPointerException e) {
            log.info("找不到串口");
        } catch (IOException e) {
            log.info("发送信息到串口时发生IO异常");
        }
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        if (serialPort != null) {
            serialPort.notifyOnDataAvailable(false);
            serialPort.removeEventListener();
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    log.info("关闭输入流时发生IO异常");
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    log.info("关闭输出流时发生IO异常");
                }
            }
            serialPort.close();
            serialPort = null;
        }
    }

    /**
     * 十六进制串口返回值获取
     */
    public String getDataHex() {
        String result = dataHex;
        // 置空执行结果
        dataHex = null;
        // 返回执行结果
        return result;
    }

    /**
     * 串口返回值获取
     */
    public String getData() {
        String result = data;
        // 置空执行结果
        data = null;
        // 返回执行结果
        return result;
    }
}
