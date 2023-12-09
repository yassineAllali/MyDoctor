package com.mydoctor.domaine.appointment.exception;

public class IllegalArgumentException extends CalendarException{

    private static final String ILLEGAL_MESSAGE = "Illegal Argument : ";
    public IllegalArgumentException(String message) {
        super(ILLEGAL_MESSAGE + message);
    }

    public IllegalArgumentException(String message, Throwable cause) {
        super(ILLEGAL_MESSAGE + message, cause);
    }
}
