package com.mydoctor.domaine.appointment.booking;

import com.mydoctor.domaine.appointment.booking.exception.BookingException;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class WorkingDay implements BookableTimeInterval {

    private final LocalDate date;
    private final List<WorkingTimeInterval> workingTimeSlots;

    public WorkingDay(LocalDate date, List<WorkingTimeInterval> workingTimeSlots) {
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

    public List<WorkingTimeInterval> getWorkingTimeSlots() {
        return new ArrayList<>(workingTimeSlots);
    }

    @Override
    public int getBookedSize() {
        return workingTimeSlots.stream().mapToInt(w -> w.getBookedSize()).sum();
    }

    @Override
    public int getAvailableSlotsSize(Duration duration) {
        return workingTimeSlots.stream().mapToInt(w -> w.getAvailableSlotsSize(duration)).sum();
    }

    @Override
    public void book(TimeSlot timeSlot) {
        if(!isInside(timeSlot)) {
            throw new BookingException("Not Inside Working Slots!");
        }
        if(isConflictWithBooked(timeSlot)) {
            throw new BookingException("Conflict with existing booked Time Slots!");
        }

        WorkingTimeInterval workingTimeSlot = getWhereInside(timeSlot)
                .orElseThrow(() -> new BookingException("Not Inside Working Slots!"));
        workingTimeSlot.book(timeSlot);
    }

    private Optional<WorkingTimeInterval> getWhereInside(TimeSlot timeSlot) {
        return workingTimeSlots.stream()
                .filter(w -> w.isInside(timeSlot))
                .findFirst();
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
