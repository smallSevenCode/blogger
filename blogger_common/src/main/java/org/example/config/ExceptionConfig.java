package org.example.config;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.common.R;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 全局异常处理
 *
 * @author: 张鹏
 * @date: 2022/1/4 20:34
 */
@Slf4j
@RestControllerAdvice
public class ExceptionConfig {

    /**
     * 异常全局处理
     *
     * @param e 异常
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R<Object> exception(Exception e) {
        e.printStackTrace();
        log.error(ExceptionUtil.stacktraceToString(e));
        return R.failed(e.getMessage());
    }

    /**
     * 自定义异常处理
     *
     * @param e 异常
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public R<Object> illegalArgumentException(IllegalArgumentException e) {
        return R.failed(e.getMessage());
    }


    /**
     * Valid参数校验异常全局处理
     *
     * @param e 异常
     * @return api result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<Object> validException(MethodArgumentNotValidException e) {
        return R.failed(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }


}
