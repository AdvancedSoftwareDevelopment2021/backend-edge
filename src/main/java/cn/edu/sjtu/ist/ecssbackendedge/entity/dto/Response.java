package cn.edu.sjtu.ist.ecssbackendedge.entity.dto;

import lombok.Data;

/**
 * @brief 标准返回类
 * @author rsp
 * @version 0.1
 * @date 2021-11-18
 */
@Data
public class Response {

    private final Integer code;

    private final String message;

    private final Object object;

    public Response(Integer code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", object=" + object +
                '}';
    }
}
