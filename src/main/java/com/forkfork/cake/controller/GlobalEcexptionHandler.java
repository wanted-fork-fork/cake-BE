package com.forkfork.cake.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalEcexptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map> handleException(Exception e) {

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("msg", e.getMessage());
        res.put("trace", Arrays.toString(e.getStackTrace()));
        return new ResponseEntity(res, HttpStatus.valueOf(500));
    }

}

