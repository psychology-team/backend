package com.psychology.product.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = Map.of(
                "message", message,
                "status", status.value()
        );
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateError(String message, HttpStatus status) {
        Map<String, Object> map = Map.of(
                "message", message,
                "status", status.value()
        );
        return new ResponseEntity<>(map, status);
    }
}
