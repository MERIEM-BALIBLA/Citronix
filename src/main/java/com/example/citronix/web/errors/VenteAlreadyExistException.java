package com.example.citronix.web.errors;

public class VenteAlreadyExistException extends RuntimeException{
    public VenteAlreadyExistException(String message) {
        super(message);
    }

}
