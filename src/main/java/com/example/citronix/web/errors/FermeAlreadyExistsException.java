package com.example.citronix.web.errors;

public class FermeAlreadyExistsException extends RuntimeException {
    public FermeAlreadyExistsException(String message) {
        super(message);
    }
}
