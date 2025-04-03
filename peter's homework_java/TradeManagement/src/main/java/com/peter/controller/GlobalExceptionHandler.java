package com.peter.controller;

import com.peter.exception.TradeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.peter.common.AjaxResult;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AjaxResult> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(AjaxResult.error(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AjaxResult> handleGeneralException(Exception e) {
        return new ResponseEntity<>(AjaxResult.error("服务内部错误"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AjaxResult> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("; ");
        }
        return new ResponseEntity<>(AjaxResult.error(errorMessage.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<AjaxResult> handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("; ");
        }
        return new ResponseEntity<>(AjaxResult.error(errorMessage.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TradeException.class)
    public ResponseEntity<AjaxResult> handleTradeException(TradeException e) {
        return new ResponseEntity<>(AjaxResult.error(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}