package com.mydoctor.application.exception;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message) {
        super(message);
    }
}
