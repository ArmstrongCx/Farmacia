package com.gestion.exception;

public class ClienteYaRegistradoException extends RuntimeException {
    public ClienteYaRegistradoException(String message) {
        super(message);
    }
}