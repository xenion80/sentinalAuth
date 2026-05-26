package com.example.demo.authservice.exceptions;

public class ResourceNotfoundException extends RuntimeException {
    public ResourceNotfoundException(String message) {
        super(message);
    }
}
