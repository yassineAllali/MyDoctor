package com.mydoctor.domaine.appointment.booking;

import com.mydoctor.domaine.exception.IllegalArgumentException;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class WorkingTimeInterval extends TimeSlot implements BookableTimeInterval {

    // Should Be ordered
    private final List<TimeSlot> booked;

    public WorkingTimeInterval(LocalTime start, LocalTime end, List<TimeSlot> booked) {
        super(start, end);
        validateBooked(booked);
        this.booked = new ArrayList<>(booked);
    }

    private void validateBooked(List<TimeSlot> booked) {
        if(booked == null)
            throw new IllegalArgumentException("Booked is null !");
        if(booked.stream().anyMatch(b -> !this.isInside(b)))
            throw new IllegalArgumentException("Booked should be inside working time slot !");
        if(!isBookedOrdered(booked))
            throw new IllegalArgumentException("Booked should be ordered !");
    }

    private boolean isBookedOrdered(List<TimeSlot> booked) {
        if(booked.size() <= 1)
            return true;
        for(int i = 0; i < booked.size() - 1; i++) {
            if(!booked.get(i).isStartAfterMyEnd(booked.get(i + 1)))
                return false;
        }
        return true;
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
        if( getBookedSize() == 0 ) {
            return getSubSlotsSize(duration);
        }

        int availableSlotsSize = 0;
        LocalTime localStart = getStart();
        LocalTime localEnd = getBooked().get(0).getStart();

        availableSlotsSize += TimeSlot.getAvailableSlotsSize(localStart, localEnd, duration);

        // Calculate available slots between booked gaps
        if(booked.size() > 1) {
            for(int i = 0; i < booked.size() - 1; i++) {
                localStart = booked.get(i).getEnd();
                localEnd = booked.get(i + 1).getStart();

                availableSlotsSize += getAvailableSlotsSize(localStart, localEnd, duration);
            }
        }

        // Add last Gap
        TimeSlot lastBooked = booked.get(booked.size() - 1);
        availableSlotsSize += getAvailableSlotsSize(lastBooked.getEnd(), getEnd(), duration);

        return availableSlotsSize;
    }

    @Override
    public List<TimeSlot> getAvailableSlots(Duration duration) {
        if( getBookedSize() == 0 ) {
            return Arrays.asList(getSubSlots(duration));
        }

        final int maxSubSlotsSize = getSubSlotsSize(duration);
        List<TimeSlot> availableSlots = new ArrayList<>(maxSubSlotsSize);

        LocalTime localStart = getStart();
        LocalTime localEnd = getBooked().get(0).getStart();

        availableSlots.addAll(Arrays.asList(getAvailableSlots(localStart, localEnd, duration)));

        // get available slots between booked gaps
        if(booked.size() > 1) {
            for(int i = 0; i < booked.size() - 1; i++) {
                localStart = booked.get(i).getEnd();
                localEnd = booked.get(i + 1).getStart();

                availableSlots.addAll(Arrays.asList(getAvailableSlots(localStart, localEnd, duration)));
            }
        }

        // Add last Gap
        TimeSlot lastBooked = booked.get(booked.size() - 1);
        availableSlots.addAll(Arrays.asList(getAvailableSlots(lastBooked.getEnd(), getEnd(), duration)));

        return availableSlots;
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
