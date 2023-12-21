package com.mydoctor.domaine.appointment.booking;

import com.mydoctor.domaine.appointment.booking.exception.BookingException;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public final class WorkingTimeInterval extends TimeSlot implements BookableTimeInterval {

    // Should Be ordered
    private final List<TimeSlot> booked;

    public WorkingTimeInterval(LocalTime start, LocalTime end, List<TimeSlot> booked) {
        super(start, end);
        this.booked = new ArrayList<>(booked);
    }

    public WorkingTimeInterval(LocalTime start, LocalTime end) {
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
    public int getAvailableSlotsSize(Duration duration) {
        int availableSlotsSize = 0;

        LocalTime localStart = getStart();
        LocalTime localEnd = (getBookedSize() > 0) ? getBooked().get(0).getStart() : getEnd();

        availableSlotsSize += TimeSlot.getAvailableSlotsSize(localStart, localEnd, duration);

        // Calculate available slots between booked gaps
        if(booked.size() > 1) {
            for(int i = 0; i < booked.size() - 1; i++) {
                localStart = booked.get(i).getEnd();
                localEnd = booked.get(i + 1).getStart();

                availableSlotsSize += TimeSlot.getAvailableSlotsSize(localStart, localEnd, duration);
            }
        }

        // Add last Gap
        TimeSlot lastBooked = booked.get(booked.size() - 1);
        availableSlotsSize += TimeSlot.getAvailableSlotsSize(lastBooked.getEnd(), getEnd(), duration);

        return availableSlotsSize;
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
