package org.example.swiftcode.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SwiftCodeNotFoundException extends RuntimeException {
    public SwiftCodeNotFoundException(String message) {
        super(message);
    }
}

