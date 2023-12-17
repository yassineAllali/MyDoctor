package com.mydoctor.domaine.appointment.booking;

public interface BookableTimeInterval extends Bookable{

    boolean isConflictWithBooked(TimeSlot timeSlot);
    boolean isInside(TimeSlot timeSlot);
    void book(TimeSlot timeSlot);
}
