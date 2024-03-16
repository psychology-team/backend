package com.psychology.product.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RequestExceptionHandler {

    public static ResponseStatusException badRequestException(String message) {
        return  new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }

}
