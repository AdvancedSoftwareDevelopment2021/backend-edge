package cn.edu.sjtu.ist.ecssbackendedge.utils.response;

import lombok.Getter;


/**
 * @author dyanjun
 * @date 2021/10/31 15:42
 */
public enum ResultCode {
    SUCCESS(0, "success"),
    ERROR(-1, "error"),
    OBJECT_NOT_FOUND(-2, "%s 不存在"),
    WRONG_PASSWORD(-3, "密码错误"),
    NON_AUTHORITY(-4, "无权限"),
    INVALID_TOKEN(-5, "无效Token"),
    NON_TOKEN(-6, "无Token"),
    SERVICE_EXCEPTION(-7, "%s"),
    WRONG_INFORMATION(-8, "输入信息有误"),
    NON_ACCOUNT(-9, "账户不存在"),
    EXIST_ACCOUNT(-10, "账号已存在"),
    RUN_TIME_EXCEPTION(-100, "运行时错误: %s");


    @Getter
    private int code;

    @Getter
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


