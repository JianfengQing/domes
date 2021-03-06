package com.lve.risk.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * 统一异常处理
 *
 * @author qingjianfeng
 * @date   2021-01-28
 * */
@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseData globalException(HttpServletResponse response, Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        String exception = baos.toString();
        //异常信息写入日志文件中
        logger.info(exception);
        if (e instanceof BusinessException){
            BusinessException businessException = (BusinessException) e;
            return ResponseData.success(200, businessException.getMsg(), null);
        }
        return ResponseData.fail("网络异常: " + e.getMessage());
    }
}
