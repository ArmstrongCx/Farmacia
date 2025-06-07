package com.gestion.exception;

public class ProveedorNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public ProveedorNotFoundException(String message) {
        super(message);
    }

    public ProveedorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
