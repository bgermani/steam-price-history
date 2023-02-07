package com.bgermani.steampricehistory.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
        public ResourceNotFoundException() {
            super();
        }
        public ResourceNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
        public ResourceNotFoundException(String message) {
            super(message);
        }
        public ResourceNotFoundException(Throwable cause) {
            super(cause);
        }
}
