package com.mydoctor.domaine.exception;

public class IllegalStateException extends DomainException{
    public IllegalStateException(String message) {
        super(message);
    }

    public IllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
