package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.command;

import cn.edu.sjtu.ist.ecssbackendedge.entity.domain.enumeration.CommandTag;
import lombok.Data;
import java.util.*;

/**
 * @author dyanjun
 * @date 2021/12/25 21:41
 */
@Data
public class Command {

    String name;

    String description;

    CommandType commandType;

    /**
     * 设置属性时,需要有数据的类型和值
     */
    String type;
    String value;

    /**
     * 自定义数据时，需要有自定义的参数
     */
    List<Param> params;

    CommandTag tag;

    String processId;

    // TODO 顺序？

    public void verify(){
        if(commandType == CommandType.CUSTOM){
            if(params == null){
                throw new RuntimeException("自定义指令时，需要输入自定义的参数");
            }
        }
        if(commandType == CommandType.PROPERTY){
            if(type == null || value == null){
                throw new RuntimeException("设置属性时，需要输入属性类型与属性值");
            }
        }
    }
}
