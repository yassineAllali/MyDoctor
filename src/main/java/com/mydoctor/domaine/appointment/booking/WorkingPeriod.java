package com.mydoctor.domaine.appointment.booking;

import com.mydoctor.domaine.exception.IllegalArgumentException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


// Not Finished
public final class WorkingPeriod implements BookablePeriod {

    private final LocalDate startInclusive;
    private final LocalDate endExclusive;
    private final List<WorkingDay> workingDays;

    public WorkingPeriod(LocalDate startInclusive, LocalDate endExclusive, List<WorkingDay> workingDays) {
        validateInputs(startInclusive, endExclusive, workingDays);
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        this.workingDays = new ArrayList<>(workingDays);
    }

    private void validateInputs(LocalDate start, LocalDate end, List<WorkingDay> workingDays) {
        validateNotNull(start, end, workingDays);
        validateStartAndEndOrder(start, end);
        validateIsWorkingDaysInside(start, end, workingDays);
        validateIsWorkingDaysOrderedAndNotDuplicated(workingDays);
    }

    private void validateNotNull(LocalDate start, LocalDate end, List<WorkingDay> workingDays) {
        if(start == null)
            throw new IllegalArgumentException("Start date is null !");
        if(end == null)
            throw new IllegalArgumentException("End date is null !");
        if(workingDays == null)
            throw new IllegalArgumentException("End date is null !");
    }

    private void validateStartAndEndOrder(LocalDate start, LocalDate end) {
        if(end.isBefore(start))
            throw new IllegalArgumentException("End date should be after start !");
    }

    private void validateIsWorkingDaysInside(LocalDate start, LocalDate end, List<WorkingDay> workingDays) {
        if(!isWorkingDaysInside(start, end, workingDays))
            throw new IllegalArgumentException("Working days should be inside period !");
    }

    private boolean isWorkingDaysInside(LocalDate start, LocalDate end, List<WorkingDay> workingDays) {
        return workingDays.stream().allMatch(w -> isInsidePeriod(start, end, w.getDate()));
    }

    private boolean isInsidePeriod(LocalDate start, LocalDate end, LocalDate date) {
        if(date.isBefore(start) || date.isAfter(end))
            return false;
        return true;
    }

    private void validateIsWorkingDaysOrderedAndNotDuplicated(List<WorkingDay> workingDays) {
        if(!isWorkingDaysOrderedAndNotDuplicated(workingDays))
            throw new IllegalArgumentException("Working days should be ordered !");
    }

    private boolean isWorkingDaysOrderedAndNotDuplicated(List<WorkingDay> workingDays) {
        for(int i = 0; i < workingDays.size() - 1; i++) {
            LocalDate currentDate = workingDays.get(i).getDate();
            LocalDate nextDate = workingDays.get(i + 1).getDate();
            if(nextDate.isEqual(currentDate) || nextDate.isBefore(currentDate))
                return false;
        }
        return true;
    }

    public WorkingPeriod(LocalDate startInclusive, LocalDate endExclusive) {
        this(startInclusive, endExclusive, new ArrayList<>());
    }

    public LocalDate getStartInclusive() {
        return startInclusive;
    }

    public LocalDate getEndExclusive() {
        return endExclusive;
    }

    public Period getPeriod() {
        return Period.between(startInclusive, endExclusive);
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
        return date.isBefore(startInclusive) || date.isAfter(endExclusive);
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
