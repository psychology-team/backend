package com.psychology.product.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ResponseUtil {
    public static ResponseEntity<?> generateResponse(String message, HttpStatus status, Object... o) {
        return getResponse(message, status, o);
    }

    public static ResponseEntity<?> generateError(String message, HttpStatus status, Object... o) {
        return getResponse(message, status, o);
    }

    private static ResponseEntity<?> getResponse(String message, HttpStatus status, Object... o) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        if (o.length > 0 && o[0] != null) {
            log.info(loggerMessage(status, message, o[0]));
            map.put("data", o[0]);
        } else {
            log.info(loggerMessage(status, message));
        }
        return new ResponseEntity<>(map, status);
    }

    private static String loggerMessage(Object... o) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < o.length; i++) {
            sb.append((i < 2) ? "\u001B[32m" : "").append(o[i].toString()).append((i < 2) ? "\u001B[0m" : "");
            if (i < o.length - 1) {
                sb.append(" - ");
            }
        }
        return sb.toString();
    }
}
