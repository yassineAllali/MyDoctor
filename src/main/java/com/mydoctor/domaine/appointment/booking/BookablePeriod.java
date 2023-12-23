package com.mydoctor.domaine.appointment.booking;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public interface BookablePeriod extends Bookable{

    boolean isConflictWithBooked(LocalDate date, TimeSlot timeSlot);
    boolean isInside(LocalDate date, TimeSlot timeSlot);
    void book(LocalDate date, TimeSlot timeSlot);
    List<TimeSlot> getAvailableSlots(LocalDate date, Duration duration);
    boolean isWorkingDay(LocalDate date);
}
