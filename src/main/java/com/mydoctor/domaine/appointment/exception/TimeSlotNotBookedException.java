package com.mydoctor.domaine.appointment.exception;

public class TimeSlotNotBookedException extends CalendarException {

    private static final String CANT_BOOK_MESSAGE = "Can't book this time slot : ";

    public TimeSlotNotBookedException(String message) {
        super(CANT_BOOK_MESSAGE + message);
    }

    public TimeSlotNotBookedException(String message, Throwable cause) {
        super(CANT_BOOK_MESSAGE + message, cause);
    }
}
