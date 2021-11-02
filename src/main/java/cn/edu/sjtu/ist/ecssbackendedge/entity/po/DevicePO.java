package cn.edu.sjtu.ist.ecssbackendedge.entity.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "device")
public class DevicePO {

    /**
     * 产品id
     */
    @Id
    private String id;

    /**
     * 产品名称
     */
    @Field
    private String name;

    /**
     * 产品型号
     */
    @Field
    private String model;

    /**
     * 通信协议
     */
    @Field
    private String messageProtocol;
}
