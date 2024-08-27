package com.example.bookstoreapi.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.OptimisticLockException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handle validation errors
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        String accept = request.getHeader("Accept");
        MediaType mediaType = MediaType.APPLICATION_JSON;
        if (accept != null && accept.contains(MediaType.APPLICATION_XML_VALUE)) {
            mediaType = MediaType.APPLICATION_XML;
        }

        ErrorResponse errorResponse = new ErrorResponse("Validation Failed", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(mediaType)
                .body(errorResponse);
    }

    // Handle resource not found exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        String accept = request.getHeader("Accept");
        MediaType mediaType = MediaType.APPLICATION_JSON;
        if (accept != null && accept.contains(MediaType.APPLICATION_XML_VALUE)) {
            mediaType = MediaType.APPLICATION_XML;
        }

        ErrorResponse errorResponse = new ErrorResponse("Error", Map.of("error", ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(mediaType)
                .body(errorResponse);
    }

    // Handle optimistic locking exceptions
    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<Object> handleOptimisticLockException(OptimisticLockException ex, WebRequest request) {
        String accept = request.getHeader("Accept");
        MediaType mediaType = MediaType.APPLICATION_JSON;
        if (accept != null && accept.contains(MediaType.APPLICATION_XML_VALUE)) {
            mediaType = MediaType.APPLICATION_XML;
        }

        ErrorResponse errorResponse = new ErrorResponse("Concurrency Error", Map.of("error", "Concurrent update detected. Please reload and try again."));
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(mediaType)
                .body(errorResponse);
    }

    // Define a generic error response class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JacksonXmlRootElement(localName = "ErrorResponse")
    public static class ErrorResponse {
        private String message;
        private Map<String, String> details;

        public ErrorResponse() {}

        public ErrorResponse(String message, Map<String, String> details) {
            this.message = message;
            this.details = details;
        }

        // Getters and setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
        
        public Map<String, String> getDetails() {
            return details;
        }

        public void setDetails(Map<String, String> details) {
            this.details = details;
        }
    }
}
