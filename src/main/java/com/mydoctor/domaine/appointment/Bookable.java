package com.mydoctor.domaine.appointment;

public interface Bookable {

    int getBookedSize();
    void book(TimeSlot timeSlot);
    boolean isConflictWithBooked(TimeSlot timeSlot);
    boolean isInside(TimeSlot timeSlot);
}
