package com.forkfork.cake.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResFormat {
    static public ResponseEntity response(boolean success, int code, Object data) {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("success", success);
        res.put("code", code);
        res.put("data", data);

        return new ResponseEntity(res, HttpStatus.valueOf(code));
    }
}
