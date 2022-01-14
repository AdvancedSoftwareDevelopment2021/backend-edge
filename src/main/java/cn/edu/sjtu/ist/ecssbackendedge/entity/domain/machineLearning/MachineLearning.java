package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.machineLearning;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author dyanjun
 * @date 2022/1/14 0:24
 */
@Data
@Document(collection = "machineLearning")
public class MachineLearning {
    @Id
    private String id;

    @Field
    private String name;
}
