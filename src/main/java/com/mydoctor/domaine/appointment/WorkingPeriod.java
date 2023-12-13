package com.mydoctor.domaine.appointment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkingPeriod implements Bookable {

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
    public void book(TimeSlot timeSlot) {

    }

    @Override
    public boolean isConflictWithBooked(TimeSlot timeSlot) {
        return false;
    }

    @Override
    public boolean isInside(TimeSlot timeSlot) {
        return false;
    }
}
