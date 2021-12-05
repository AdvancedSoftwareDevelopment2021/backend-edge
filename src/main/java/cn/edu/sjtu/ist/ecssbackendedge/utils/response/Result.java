package cn.edu.sjtu.ist.ecssbackendedge.utils.response;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/10/31 15:42
 */
@Data
public class Result<T> {

    /**
     * 数据
     */
    T data;

    /**
     * 消息
     */
    String message;

    /**
     * 代码
     */
    int code;
}