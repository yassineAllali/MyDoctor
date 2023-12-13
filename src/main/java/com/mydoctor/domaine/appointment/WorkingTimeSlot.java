package com.mydoctor.domaine.appointment;

import com.mydoctor.domaine.appointment.exception.BookingException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public final class WorkingTimeSlot extends TimeSlot implements Bookable{

    private final List<TimeSlot> booked;

    public WorkingTimeSlot(LocalTime start, LocalTime end, List<TimeSlot> booked) {
        super(start, end);
        this.booked = new ArrayList<>(booked);
    }

    public WorkingTimeSlot(LocalTime start, LocalTime end) {
        this(start, end, new ArrayList<>());
    }

    public List<TimeSlot> getBooked() {
        return List.copyOf(booked);
    }

    @Override
    public int getBookedSize() {
        return booked.size();
    }

    @Override
    public void book(TimeSlot timeSlot) {
        if(!isInside(timeSlot)) {
            throw new BookingException("Outside working slot!");
        }
        if(isConflictWithBooked(timeSlot)) {
            throw new BookingException("Conflict with existing booked Time Slots!");
        }
        booked.add(timeSlot);
    }

    @Override
    public boolean isConflictWithBooked(TimeSlot timeSlot) {
        return booked.stream().anyMatch(b -> b.isConflict(timeSlot));
    }

}
