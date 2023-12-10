package com.mydoctor.domaine.appointment;

import com.mydoctor.domaine.appointment.exception.BookingException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class WorkingDay implements Bookable{

    private final LocalDate date;
    private final List<WorkingTimeSlot> workingTimeSlots;

    public WorkingDay(LocalDate date, List<WorkingTimeSlot> workingTimeSlots) {
        this.date = date;
        this.workingTimeSlots = new ArrayList<>(workingTimeSlots);
    }

    public WorkingDay(LocalDate date) {
        this(date, new ArrayList<>());
    }

    public LocalDate getDate() {
        return date;
    }

    public DayOfWeek getDayOfWeek() {
        return DayOfWeek.from(date);
    }

    public List<WorkingTimeSlot> getWorkingTimeSlots() {
        return new ArrayList<>(workingTimeSlots);
    }

    @Override
    public int getBookedSize() {
        return workingTimeSlots.stream().mapToInt(w -> w.getBookedSize()).sum();
    }

    @Override
    public void book(TimeSlot timeSlot) {
        if(!isInside(timeSlot)) {
            throw new BookingException("Not Inside Working Slots!");
        }
        if(isConflictWithBooked(timeSlot)) {
            throw new BookingException("Conflict with existing booked Time Slots!");
        }

        WorkingTimeSlot workingTimeSlot = getWhereInside(timeSlot);
        workingTimeSlot.book(timeSlot);
    }

    private WorkingTimeSlot getWhereInside(TimeSlot timeSlot) {
        return workingTimeSlots.stream()
                .filter(w -> w.isInside(timeSlot))
                .findFirst()
                .orElseThrow(() -> new BookingException("Time Slot is not Inside Working Slots!"));
    }

    @Override
    public boolean isConflictWithBooked(TimeSlot timeSlot) {
        return workingTimeSlots.stream().anyMatch(w -> w.isConflictWithBooked(timeSlot));
    }

    @Override
    public boolean isInside(TimeSlot timeSlot) {
        return workingTimeSlots.stream().anyMatch(w -> w.isInside(timeSlot));
    }

}
