package cn.edu.sjtu.ist.ecssbackendedge.utils.response;

import lombok.Data;

/**
 * @author dyanjun
 * @date 2021/10/31 15:42
 */
@Data
public class Result<T> {
    T data;

    String message;

    int code;
}