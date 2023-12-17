package com.mydoctor.domaine.appointment.booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
    public void book(LocalDate date, TimeSlot timeSlot) {
        // TODO
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
