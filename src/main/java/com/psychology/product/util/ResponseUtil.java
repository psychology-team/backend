package com.psychology.product.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class ResponseUtil {
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

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObject) {
        Map<String, Object> map = Map.of(
                "message", message,
                "status", status.value(),
                "data", responseObject
        );
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<?> generateError(String message, HttpStatus status, List<Map<String, String>> errors) {
        Map<String, Object> map = Map.of(
                "message", message,
                "status", status.value(),
                "data", errors
        );
        return new ResponseEntity<>(map, status);
    }
}
