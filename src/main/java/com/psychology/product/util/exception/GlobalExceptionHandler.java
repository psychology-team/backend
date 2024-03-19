package com.psychology.product.util.exception;

import com.psychology.product.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException e) {
        return ResponseUtil.generateError(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> conflict(ConflictException e) {
        return ResponseUtil.generateError(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFound(NotFoundException e) {
        return ResponseUtil.generateError(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
