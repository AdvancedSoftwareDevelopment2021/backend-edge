package cn.edu.sjtu.ist.ecssbackendedge.utils.collect;

import cn.edu.sjtu.ist.ecssbackendedge.config.ModbusConfig;
import cn.edu.sjtu.ist.ecssbackendedge.model.sensor.function.ModbusFunction;

import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.msg.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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

    public String collectData(String id, String ip, Integer port, Integer slaveId, Integer offset, Integer num, ModbusFunction modbusFunction, String datatype) throws ModbusTransportException {
        if(slaveId != null) this.slaveId = slaveId;
        String output = "";
        switch(modbusFunction){
            case COIL_STATUS:
                boolean[] coilStatus = readCoilStatus(id, ip, port, offset, num);
                output = Arrays.toString(coilStatus);
                break;
            case INPUT_STATUS:
                boolean[] inputStatus = readInputStatus(id, ip, port, offset, num);
                output = Arrays.toString(inputStatus);
                break;
            case HOLDING_REGISTER:
                byte[] holdingRegister = readHoldingRegister(id, ip, port, offset, num, datatype);
                byte[] e = new byte[holdingRegister.length/2];
                for(int i=0;i<holdingRegister.length;i++){
                    if(i%2 != 0){
                        e[i/2] = holdingRegister[i];
                    }
                }
                output = Arrays.toString(e);
                break;
            case INPUT_REGISTER:
                byte[] inputRegisters = readInputRegisters(id, ip, port, offset, num, datatype);
                output = Arrays.toString(inputRegisters);
                break;
            default:
                break;
        }
        return output;
    }

    /**
     * @Title readCoilStatus
     * @Description: 读（线圈）开关量数据，相当于功能码：01H-读线圈状态
     * @params: [ip, slaveId, offset, numberOfRegister]
     * @return: boolean[]
     * @throws: ModbusTransportException Modbus传输异常
     */
    public boolean[] readCoilStatus(String id, String ip, Integer port, int offset, int numberOfRegister) throws ModbusTransportException {

        ModbusMaster master = modbusConfig.getMaster(id, ip, port);
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
     * @throws: ModbusTransportException Modbus传输异常
     */
    public boolean[] readInputStatus(String id, String ip, Integer port, int offset, int numberOfRegister) throws ModbusTransportException {

        ModbusMaster master = modbusConfig.getMaster(id, ip, port);
        ReadDiscreteInputsRequest request = new ReadDiscreteInputsRequest(slaveId,offset, numberOfRegister);
        ReadDiscreteInputsResponse response = (ReadDiscreteInputsResponse) master.send(request);
        boolean[] booleans = response.getBooleanData();

        return valueRegroup(numberOfRegister, booleans);
    }

    /**
     * @Title readHoldingRegister
     * @Description: 读取保持寄存器数据，相当于功能码：03H-读保持寄存器
     * @params: [ip, offset, numberOfRegister]
     * @return:  Number[]
     * @throws: ModbusTransportException Modbus传输异常
     */
    public byte[] readHoldingRegister(String id, String ip, Integer port, int offset, int numberOfRegister, String datatype) throws ModbusTransportException {

        ModbusMaster master = modbusConfig.getMaster(id, ip, port);
        ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, offset, numberOfRegister);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);
        return response.getData();
    }

    /**
     * @Title readInputRegisters
     * @Description: 读取外围设备输入的数据，相当于功能码：04H-读输入寄存器
     * @params: [ip, offset, numberOfRegister]
     * @return: Number[]
     * @throws: ModbusTransportException Modbus传输异常
     */
    public byte[] readInputRegisters(String id, String ip, Integer port, int offset, int numberOfRegister, String datatype) throws ModbusTransportException {
        ModbusMaster master = modbusConfig.getMaster(id, ip, port);
        ReadInputRegistersRequest request = new ReadInputRegistersRequest(slaveId, offset, numberOfRegister);
        ReadInputRegistersResponse response = (ReadInputRegistersResponse) master.send(request);
        return response.getData();
    }

    /**
     * @Title writeCoil
     * @Description: 写单个（线圈）开关量数据，相当于功能码：05H-写单个线圈
     * @params: [ip, writeOffset, writeValue]
     * @return: boolean
     * @throws: ModbusTransportException Modbus传输异常
     */
    public boolean writeCoil(String id, String ip, Integer port, int writeOffset, boolean writeValue) throws ModbusTransportException {

        ModbusMaster tcpMaster = modbusConfig.getMaster(id, ip, port);
        WriteCoilRequest request = new WriteCoilRequest(slaveId, writeOffset, writeValue);
        WriteCoilResponse response = (WriteCoilResponse) tcpMaster.send(request);
        return !response.isException();
    }

    /**
     * @Title writeCoils
     * @Description: 写多个开关量数据（线圈），相当于功能码：0FH-写多个线圈
     * @params: [ip, startOffset, data]
     * @return: boolean
     * @throws: ModbusTransportException Modbus传输异常
     */
    public boolean writeCoils(String id, String ip, Integer port, int startOffset, boolean[] data) throws ModbusTransportException {

        ModbusMaster tcpMaster = modbusConfig.getMaster(id, ip, port);
        WriteCoilsRequest request = new WriteCoilsRequest(slaveId, startOffset, data);
        WriteCoilsResponse response = (WriteCoilsResponse) tcpMaster.send(request);
        return !response.isException();

    }

    /**
     * @Title writeHoldingRegister
     * @Description: 写单个保持寄存器，相当于功能码：06H-写单个保持寄存器
     * @params: [ip, writeOffset, writeValue]
     * @return: boolean
     * @throws: ModbusTransportException Modbus传输异常
     */
    public boolean writeHoldingRegister(String id, String ip, Integer port, int writeOffset, short writeValue) throws ModbusTransportException, ModbusInitException {

        ModbusMaster tcpMaster = modbusConfig.getMaster(id, ip, port);
        WriteRegisterRequest request = new WriteRegisterRequest(slaveId, writeOffset, writeValue);
        WriteRegisterResponse response = (WriteRegisterResponse) tcpMaster.send(request);
        return !response.isException();

    }

    /**
     * @Title writeHoldingRegisters
     * @Description: 写多个保持寄存器，相当于功能码：10H-写多个保持寄存器
     * @params: [ip, slaveId, startOffset, data]
     * @return: boolean
     * @throws: ModbusTransportException Modbus传输异常
     */
    public boolean writeHoldingRegisters(String id, String ip, Integer port, int startOffset, short[] data) throws ModbusTransportException, ModbusInitException {

        ModbusMaster tcpMaster = modbusConfig.getMaster(id, ip, port);
        WriteRegistersRequest request = new WriteRegistersRequest(slaveId, startOffset, data);
        WriteRegistersResponse response = (WriteRegistersResponse) tcpMaster.send(request);
        return !response.isException();
    }

    /**
     * @Title valueRegroup
     * @Description: 转换工具，将Boolean转换成0，1
     * @params: [numberOfBits, values]
     * @return: boolean[]
     */
    private boolean[] valueRegroup(int numberOfBits, boolean[] values) {
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
