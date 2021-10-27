package cn.edu.sjtu.ist.ecssbackendedge.utils;


import cn.edu.sjtu.ist.ecssbackendedge.configuration.ModbusConfig;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.msg.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author dyanjun
 * @date 2021/10/27 20:51
 */
@Component
public class ModbusUtil {
    //从机默认值
    private Integer slaveId = 1;

    @Autowired
    private ModbusConfig modbusConfig;

    /**
     * @Title readCoilStatus
     * @Description: 读（线圈）开关量数据，相当于功能码：01H-读线圈状态
     * @params: [ip, slaveId, offset, numberOfRegister]
     * @return: boolean[]
     * @throws:
     */
    public boolean[] readCoilStatus(String ip, int offset, int numberOfRegister) throws ModbusTransportException {

        ModbusMaster master = modbusConfig.getMaster(ip);
        ReadCoilsRequest request = new ReadCoilsRequest(slaveId, offset, numberOfRegister);
        ReadCoilsResponse response = (ReadCoilsResponse) master.send(request);
        boolean[] booleans = response.getBooleanData();

        return valueRegroup(numberOfRegister, booleans);
    }

    /**
     * @Title readInputStatus
     * @Description: 读取外围设备输入的开关量，相当于功能码：02H-读离散输入状态
     * @params: [ip, offset, numberOfRegister]
     * @return: boolean[]
     * @throws:
     */
    public boolean[] readInputStatus(String ip, int offset, int numberOfRegister) throws ModbusTransportException {

        ModbusMaster master = modbusConfig.getMaster(ip);
        ReadDiscreteInputsRequest request = new ReadDiscreteInputsRequest(slaveId,offset, numberOfRegister);
        ReadDiscreteInputsResponse response = (ReadDiscreteInputsResponse) master.send(request);
        boolean[] booleans = response.getBooleanData();

        return valueRegroup(numberOfRegister, booleans);
    }

    /**
     * @Title readHoldingRegister
     * @Description: 读取保持寄存器数据，相当于功能码：03H-读保持寄存器
     * @params: [ip, offset, numberOfRegister]
     * @return: short[]
     * @throws:
     */
    public short[] readHoldingRegister(String ip, int offset, int numberOfRegister) throws ModbusTransportException {

        ModbusMaster master = modbusConfig.getMaster(ip);
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, offset, numberOfRegister);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);
        return response.getShortData();
    }

    /**
     * @Title readInputRegisters
     * @Description: 读取外围设备输入的数据，相当于功能码：04H-读输入寄存器
     * @params: [ip, offset, numberOfRegister]
     * @return: short[]
     * @throws:
     */
    public short[] readInputRegisters(String ip, int offset, int numberOfRegister) throws ModbusTransportException {

        ModbusMaster master = modbusConfig.getMaster(ip);
        ReadInputRegistersRequest request = new ReadInputRegistersRequest(slaveId, offset, numberOfRegister);
        ReadInputRegistersResponse response = (ReadInputRegistersResponse) master.send(request);
        return response.getShortData();
    }

    /**
     * @Title writeCoil
     * @Description: 写单个（线圈）开关量数据，相当于功能码：05H-写单个线圈
     * @params: [ip, writeOffset, writeValue]
     * @return: boolean
     * @throws:
     */
    public boolean writeCoil(String ip, int writeOffset, boolean writeValue) throws ModbusTransportException {

        ModbusMaster tcpMaster = modbusConfig.getMaster(ip);
        WriteCoilRequest request = new WriteCoilRequest(slaveId, writeOffset, writeValue);
        WriteCoilResponse response = (WriteCoilResponse) tcpMaster.send(request);
        return !response.isException();
    }

    /**
     * @Title writeCoils
     * @Description: 写多个开关量数据（线圈），相当于功能码：0FH-写多个线圈
     * @params: [ip, startOffset, data]
     * @return: boolean
     * @throws:
     */
    public boolean writeCoils(String ip, int startOffset, boolean[] data) throws ModbusTransportException {

        ModbusMaster tcpMaster = modbusConfig.getMaster(ip);
        WriteCoilsRequest request = new WriteCoilsRequest(slaveId, startOffset, data);
        WriteCoilsResponse response = (WriteCoilsResponse) tcpMaster.send(request);
        return !response.isException();

    }

    /**
     * @Title writeHoldingRegister
     * @Description: 写单个保持寄存器，相当于功能码：06H-写单个保持寄存器
     * @params: [ip, writeOffset, writeValue]
     * @return: boolean
     * @throws:
     */
    public boolean writeHoldingRegister(String ip, int writeOffset, short writeValue) throws ModbusTransportException, ModbusInitException {

        ModbusMaster tcpMaster = modbusConfig.getMaster(ip);
        WriteRegisterRequest request = new WriteRegisterRequest(slaveId, writeOffset, writeValue);
        WriteRegisterResponse response = (WriteRegisterResponse) tcpMaster.send(request);
        return !response.isException();

    }

    /**
     * @Title writeHoldingRegisters
     * @Description: 写多个保持寄存器，相当于功能码：10H-写多个保持寄存器
     * @params: [ip, slaveId, startOffset, data]
     * @return: boolean
     * @throws:
     */
    public boolean writeHoldingRegisters(String ip, int startOffset, short[] data) throws ModbusTransportException, ModbusInitException {

        ModbusMaster tcpMaster = modbusConfig.getMaster(ip);
        WriteRegistersRequest request = new WriteRegistersRequest(slaveId, startOffset, data);
        WriteRegistersResponse response = (WriteRegistersResponse) tcpMaster.send(request);
        return !response.isException();
    }

    /**
     * @Title valueRegroup
     * @Description: 转换工具，将Boolean转换成0，1
     * @params: [numberOfBits, values]
     * @return: boolean[]
     * @throws:
     */
    private  boolean[] valueRegroup(int numberOfBits, boolean[] values) {
        boolean[] bs = new boolean[numberOfBits];
        int temp = 1;
        for (boolean b : values) {
            bs[temp - 1] = b;
            temp++;
            if (temp > numberOfBits) {
                break;
            }
        }
        return bs;
    }

}
