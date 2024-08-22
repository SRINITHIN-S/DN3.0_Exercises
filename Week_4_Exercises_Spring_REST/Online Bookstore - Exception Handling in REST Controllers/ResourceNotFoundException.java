package com.example.bookstoreapi.exception;

public class ResourceNotFoundException extends RuntimeException {

    private final String path;

    public ResourceNotFoundException(String message, String path) {
        super(message);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
