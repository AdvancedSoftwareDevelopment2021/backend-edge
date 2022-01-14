package cn.edu.sjtu.ist.ecssbackendedge.entity.domain.machineLearning;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dyanjun
 * @date 2022/1/14 7:40
 */
@Data
@Document(collection = "picture")
public class Picture {
    @Id
    private String id;

    @Field
    private String timestamp;

    @Field
    private MultipartFile file;

    @Field
    private String mlModalId;
}
