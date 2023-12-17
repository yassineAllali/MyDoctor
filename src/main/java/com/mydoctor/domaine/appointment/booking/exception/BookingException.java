package com.mydoctor.domaine.appointment.booking.exception;

public class BookingException extends TimeSlotException {

    private static final String CANT_BOOK_MESSAGE = "Can't book this time slot : ";

    public BookingException(String message) {
        super(CANT_BOOK_MESSAGE + message);
    }

    public BookingException(String message, Throwable cause) {
        super(CANT_BOOK_MESSAGE + message, cause);
    }
}
