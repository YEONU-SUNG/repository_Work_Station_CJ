package com.neo.visitor.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(value = 1)
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleException(HttpServletRequest request, Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>("처리실패, 다시시도해주세요.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<?> handleIllegalArgumentException(HttpServletRequest request, Exception ex) {
        return new ResponseEntity<>("처리실패 (사유 : "+ex.getMessage()+")", HttpStatus.BAD_REQUEST);
    }
}