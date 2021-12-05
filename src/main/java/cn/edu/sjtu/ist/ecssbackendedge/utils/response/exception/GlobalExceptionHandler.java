package cn.edu.sjtu.ist.ecssbackendedge.utils.response.exception;

import cn.edu.sjtu.ist.ecssbackendedge.utils.response.Result;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultCode;
import cn.edu.sjtu.ist.ecssbackendedge.utils.response.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler()
    @ResponseBody
    ResponseEntity handle(RuntimeException e) {
        log.error("", e);
        Result result = ResultUtil.failure(e.getMessage(), ResultCode.RUN_TIME_EXCEPTION.getCode());
        return new ResponseEntity(result, HttpStatus.OK);
    }

    private ResponseEntity generateFailResponseEntity(ResultCode resultCode, HttpStatus ok, String... message) {
        String unformattedMessage = resultCode.getMessage();
        int code = resultCode.getCode();
        Result result = ResultUtil.failure(String.format(unformattedMessage, message), code);
        return new ResponseEntity(result, ok);
    }

}
