package cn.edu.sjtu.ist.ecssbackendedge.entity.dto.response;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/12/5 17:13
 */
@Data
public class CommandResponse {

    boolean isSuccess;

    String message;

    public CommandResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
