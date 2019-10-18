package com.tw.apistackbase.Handler;

import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler extends Exception {

    @ExceptionHandler
    @ResponseBody
    public String handleNotFoundException(NotFoundException e) {
        return e.getMessage();
    }
}
