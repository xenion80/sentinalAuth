package com.example.demo.authservice.advices;

import com.example.demo.authservice.exceptions.IdentityAlreadyExistsException;
import com.example.demo.authservice.exceptions.ResourceNotfoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.core.AuthenticationException;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotfoundException.class)
    public ResponseEntity<ApiError> handleResourceNotfoundException(ResourceNotfoundException exception){
        ApiError error=new ApiError(HttpStatus.NOT_FOUND,exception.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException exception){
        ApiError error=new ApiError(HttpStatus.UNAUTHORIZED,exception.getMessage());
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException exception){
        ApiError error=new ApiError(HttpStatus.UNAUTHORIZED,exception.getMessage());
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException exception){
        ApiError error=new ApiError(HttpStatus.BAD_REQUEST,exception.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IdentityAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleIdentityAlreadyExistsException(IdentityAlreadyExistsException exception){
        ApiError error=new ApiError(HttpStatus.UNAUTHORIZED,exception.getMessage());
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,"Something went wrong"));
    }
}
