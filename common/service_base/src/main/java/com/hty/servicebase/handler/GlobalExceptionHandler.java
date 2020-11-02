package com.hty.servicebase.handler;

import com.hty.servicebase.MyException;
import com.hty.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * create by Semineces on 2020-08-07
 * 统一异常返回类 返回json格式
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //全局异常
    @ExceptionHandler(Exception.class)
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行全局异常处理");

    }

    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行ArithmeticException异常处理");
    }

    //自定义异常
    @ExceptionHandler(MyException.class)
    public R error(MyException e) {
        //程序出现异常 把异常输出到文件中
        log.error(e.getMsg());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
