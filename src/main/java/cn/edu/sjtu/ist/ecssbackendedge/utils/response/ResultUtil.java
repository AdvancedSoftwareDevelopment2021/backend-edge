package cn.edu.sjtu.ist.ecssbackendedge.utils.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


import static cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultCode.SUCCESS;
/**
 * @author dyanjun
 * @date 2021/10/31 15:41
 */
public class ResultUtil {
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setMessage(SUCCESS.getMessage());
        result.setCode(SUCCESS.getCode());
        return result;
    }

    public static Result<String> success() {
        Result<String> result = new Result<>();
        result.setMessage("");
        result.setMessage(SUCCESS.getMessage());
        result.setCode(SUCCESS.getCode());
        return result;
    }

    public static <T> Result<T> failure(String message, int code) {
        Result<T> result = new Result<>();
        result.setMessage(message);
        result.setCode(code);
        return result;
    }

    public static <T> Result<T> failure(ResultCode resultCode) {
        return failure(resultCode.getMessage(), resultCode.getCode());
    }

    public static Mono<Void> writeError(ServerWebExchange exchange, ResultCode resultCode) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        try {
            return response.writeWith(
                    Flux.just(response.bufferFactory()
                            .wrap(JsonUtil.writeValueAsBytes(failure(resultCode)))));
        } catch (JsonProcessingException e) {
            return response.setComplete();
        }
    }
}

