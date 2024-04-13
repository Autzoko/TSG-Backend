package com.example.tsgbackend.common.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil {
    /**
     * @Description: Success Operation
     * @Param: [code, data]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Object>
     */
    public static ResponseEntity<Object> success(boolean code, Object data) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("success", code);
        map.put("data", data);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * @Description: Failed Operation
     * @Param: [code, msg]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Object>
     */
    public static ResponseEntity<Object> fail(boolean code, String msg) {
        Map<String, Object> map = new HashMap<>(4);
        map.put("success", code);
        map.put("msg", msg);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
