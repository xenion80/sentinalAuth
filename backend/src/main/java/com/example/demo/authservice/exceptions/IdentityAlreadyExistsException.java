package com.example.demo.authservice.exceptions;

public class IdentityAlreadyExistsException extends RuntimeException {
    public IdentityAlreadyExistsException(String message) {
        super(message);
    }
}
