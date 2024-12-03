package com.rr.store.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Represents the structure of API error responses.
 */
public class ApiError {

    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;

    /**
     * Constructs an ApiError with the given status and message.
     * 
     * @param status  the HTTP status
     * @param message the error message
     */
    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
