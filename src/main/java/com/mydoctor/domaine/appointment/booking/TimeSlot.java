package com.mydoctor.domaine.appointment.booking;

import com.mydoctor.domaine.appointment.booking.exception.IllegalArgumentException;

import java.time.Duration;
import java.time.LocalTime;


// Immutable
public class TimeSlot{
    // All attributes types are immutable
    // That why returned in getters
    private final LocalTime start;
    private final LocalTime end;

    public TimeSlot(LocalTime start, LocalTime end) {
        if(end.isBefore(start)) {
            throw new IllegalArgumentException("Start time must be before end time !");
        }
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }

    public boolean isBefore(TimeSlot other) {
        return other.end.isBefore(start) || other.end.equals(start);
    }

    public boolean isAfter(TimeSlot other) {
        return other.start.isAfter(end) || other.start.equals(end);
    }

    public boolean isStartEqually(TimeSlot other) {
        return other.start.equals(start);
    }

    public boolean isEndEqually(TimeSlot other) {
        return other.end.equals(end);
    }

    public boolean isStartBefore(TimeSlot other) {
        return other.start.isBefore(start);
    }

    public boolean isStartAfter(TimeSlot other) {
        return other.start.isAfter(start);
    }

    public boolean isEndBefore(TimeSlot other) {
        return other.end.isBefore(end);
    }

    public boolean isEndAfter(TimeSlot other) {
        return other.end.isAfter(end);
    }

    public boolean isStartBeforeOrEqually(TimeSlot other) {
        return isStartBefore(other) || isStartEqually(other);
    }

    public boolean isStartAfterOrEqually(TimeSlot other) {
        return isStartAfter(other) || isStartEqually(other);
    }

    public boolean isEndBeforeOrEqually(TimeSlot other) {
        return isEndBefore(other) || isEndEqually(other);
    }

    public boolean isEndAfterOrEqually(TimeSlot other) {
        return isEndAfter(other) || isEndEqually(other);
    }

    public boolean isEndBeforeMyStart(TimeSlot other) {
        return isStartBefore(other) && (other.end.isBefore(start) || other.end.equals(start));
    }

    public boolean isStartAfterMyEnd(TimeSlot other) {
        return other.start.isAfter(end) || other.start.equals(end);
    }

    public boolean isInside(TimeSlot other) {
        return isStartAfterOrEqually(other) && isEndBeforeOrEqually(other);
    }

    public boolean isOutside(TimeSlot other) {
        return isEndBeforeMyStart(other) || isStartAfterMyEnd(other);
    }

    // No conflict only if other End is before my start
    // or other start is after my end
    public boolean isConflict(TimeSlot other) {
        return !isOutside(other);
    }


}
