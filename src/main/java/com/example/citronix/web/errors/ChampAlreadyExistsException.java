package com.example.citronix.web.errors;

public class ChampAlreadyExistsException extends RuntimeException {
    public ChampAlreadyExistsException(String message) {
        super(message);
    }
}
