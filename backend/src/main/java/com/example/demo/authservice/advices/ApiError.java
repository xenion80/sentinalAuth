package com.example.demo.authservice.advices;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
@Data
public class ApiError {
    private LocalDateTime timestamp;
    private String error;
    private HttpStatusCode statusCode;

    public ApiError(){
        this.timestamp=LocalDateTime.now();
    }
    public ApiError(HttpStatusCode statusCode,String error){
        this();
        this.statusCode=statusCode;
        this.error=error;
    }
}
