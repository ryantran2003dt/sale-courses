package com.salecourseweb.exception;

public class SystemErrorException extends RuntimeException {
    public SystemErrorException(String message) {
        super(message);
    }
}
