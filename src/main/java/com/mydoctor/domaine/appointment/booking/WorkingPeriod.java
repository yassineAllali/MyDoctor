package com.mydoctor.domaine.appointment.booking;

import com.mydoctor.domaine.appointment.booking.exception.BookingException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


// Not Finished
public final class WorkingPeriod implements BookablePeriod {

    private final LocalDate start;
    private final LocalDate end;
    private final List<WorkingDay> workingDays;

    public WorkingPeriod(LocalDate start, LocalDate end, List<WorkingDay> workingDays) {
        this.start = start;
        this.end = end;
        this.workingDays = new ArrayList<>(workingDays);
    }

    public WorkingPeriod(LocalDate start, LocalDate end) {
        this(start, end, new ArrayList<>());
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public List<WorkingDay> getWorkingDays() {
        return List.copyOf(workingDays);
    }

    @Override
    public int getBookedSize() {
        return workingDays.stream().mapToInt(w -> w.getBookedSize()).sum();
    }

    @Override
    public int getAvailableSlotsSize(Duration duration) {
        return 0;
    }

    @Override
    public void book(LocalDate date, TimeSlot timeSlot) {
        if(!isInside(date, timeSlot)) {
            throw new BookingException("Not Inside Working Slots!");
        }
        if(isConflictWithBooked(date, timeSlot)) {
            throw new BookingException("Conflict with existing booked Time Slots!");
        }
        WorkingDay workingDay = getWhereInside(date)
                .orElseThrow(() -> new BookingException("Not Inside Working Slots!"));
        workingDay.book(timeSlot);
    }

    private Optional<WorkingDay> getWhereInside(LocalDate date) {
        return workingDays.stream()
                .filter(d -> d.getDate().equals(date))
                .findFirst();
    }

    @Override
    public boolean isConflictWithBooked(LocalDate date, TimeSlot timeSlot) {
        return workingDays.stream()
                .anyMatch(w -> w.getDate().equals(date) && w.isConflictWithBooked(timeSlot));
    }

    @Override
    public boolean isInside(LocalDate date, TimeSlot timeSlot) {
        return workingDays.stream()
                .anyMatch(w -> w.getDate().equals(date) && w.isInside(timeSlot));
    }
}
