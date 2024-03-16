package com.psychology.product.util;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationHandler {
    public ResponseEntity<?> bindingResultHandler(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errorMap);
    }
}
