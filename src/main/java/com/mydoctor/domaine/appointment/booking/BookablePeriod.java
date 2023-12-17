package com.mydoctor.domaine.appointment.booking;

import java.time.LocalDate;

public interface BookablePeriod extends Bookable{

    boolean isConflictWithBooked(LocalDate date, TimeSlot timeSlot);
    boolean isInside(LocalDate date, TimeSlot timeSlot);
    void book(LocalDate date, TimeSlot timeSlot);
}
