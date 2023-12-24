package com.mydoctor.domaine.appointment.booking;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
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

    public Period getPeriod() {
        return Period.between(start, end);
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
        return workingDays.stream()
                .mapToInt(w -> w.getAvailableSlotsSize(duration)).sum();
    }

    @Override
    public List<TimeSlot> getAvailableSlots(Duration duration) {
        return workingDays.stream()
                .map(w -> w.getAvailableSlots(duration))
                .flatMap(list -> list.stream()).toList();
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

    @Override
    public List<TimeSlot> getAvailableSlots(LocalDate date, Duration duration) {
        if(!isWorkingDay(date))
            return List.of();

        Optional<WorkingDay> workingDay = getWorkingDay(date);
        if(workingDay.isEmpty())
            return List.of();
        return workingDay.get().getAvailableSlots(duration);
    }

    public Optional<WorkingDay> getWorkingDay(LocalDate date) {
        return workingDays.stream().filter(d -> d.getDate().equals(date)).findFirst();
    }

    @Override
    public boolean isWorkingDay(LocalDate date) {
        if(isOutsideWorkingPeriod(date) || !isInsideWorkingDaysList(date))
            return false;
        return true;
    }

    public boolean isOutsideWorkingPeriod(LocalDate date) {
        return date.isBefore(start) || date.isAfter(end);
    }

    private boolean isInsideWorkingDaysList(LocalDate date) {
        return workingDays.stream().anyMatch(w -> w.getDate().equals(date));
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
