package cn.edu.sjtu.ist.ecssbackendedge.utils.point;

import cn.edu.sjtu.ist.ecssbackendedge.config.ModbusConfig;
import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.point.function.ModbusFunction;

import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.serotonin.modbus4j.msg.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static cn.edu.sjtu.ist.ecssbackendedge.utils.convert.DataUtil.value;


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

    public String collectData(String id, String ip, Integer port, Integer slaveId, Integer offset, ModbusFunction modbusFunction) throws ModbusTransportException, ErrorResponseException {
        if (slaveId != null) this.slaveId = slaveId;
        String type = "integer";
        ModbusMaster modbusMaster = modbusConfig.getMaster(id, ip, port);
        switch (modbusFunction) {
            case COIL_STATUS:
                BaseLocator<Boolean> coilLocator = BaseLocator.coilStatus(this.slaveId, offset);
                Boolean coilValue = modbusMaster.getValue(coilLocator);
                return String.valueOf(coilValue);
            case INPUT_STATUS:
                BaseLocator<Boolean> inputLocator = BaseLocator.inputStatus(this.slaveId, offset);
                Boolean inputStatusValue = modbusMaster.getValue(inputLocator);
                return String.valueOf(inputStatusValue);
            case HOLDING_REGISTER:
                BaseLocator<Number> holdingLocator = BaseLocator.holdingRegister(this.slaveId, offset, getValueType(type));
                Number holdingValue = modbusMaster.getValue(holdingLocator);
                return String.valueOf(holdingValue);
            case INPUT_REGISTER:
                BaseLocator<Number> inputRegister = BaseLocator.inputRegister(this.slaveId, offset, getValueType(type));
                Number inputRegisterValue = modbusMaster.getValue(inputRegister);
                return String.valueOf(inputRegisterValue);
            default:
                break;
        }
        return null;
    }

    public boolean writeValue(String id, String ip, Integer port, Integer slaveId, Integer offset, ModbusFunction modbusFunction, String type, String new_value) throws ModbusTransportException, ErrorResponseException {
        if (slaveId != null) this.slaveId = slaveId;
        type = "integer";
        ModbusMaster modbusMaster = modbusConfig.getMaster(id, ip, port);
        switch (modbusFunction) {
            case COIL_STATUS:
                boolean coilValue = value(type, new_value);
                WriteCoilRequest coilRequest = new WriteCoilRequest(this.slaveId, offset, coilValue);
                WriteCoilResponse coilResponse = (WriteCoilResponse) modbusMaster.send(coilRequest);
                return !coilResponse.isException();
            case HOLDING_REGISTER:
                BaseLocator<Number> locator = BaseLocator.holdingRegister(this.slaveId, offset, getValueType(type));
                modbusMaster.setValue(locator, value(type, new_value));
                return true;
            default:
                break;
        }
        return false;
    }

    /**
     * 获取数据类型
     * 说明：此处可根据实际项目情况进行拓展
     * 1.swap 交换
     * 2.大端/小端,默认是大端
     * 3.拓展其他数据类型
     *
     * @param type Value Type
     * @return Modbus Data Type
     */
    public int getValueType(String type) {
        System.out.println(type);
        switch (Objects.requireNonNull(cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.DataType.fromString(type.toLowerCase()))) {
            case LONG:
                return DataType.FOUR_BYTE_INT_SIGNED;
            case FLOAT:
                return DataType.FOUR_BYTE_FLOAT;
            case DOUBLE:
                return DataType.EIGHT_BYTE_FLOAT;
            default:
                return DataType.TWO_BYTE_INT_SIGNED;
        }
    }

}
