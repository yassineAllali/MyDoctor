package com.mydoctor.domaine.appointment;

import com.mydoctor.domaine.appointment.exception.IllegalArgumentException;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;


// Immutable
public abstract class TimeSlot {
    // All attributes types are immutable
    // That why returned in getters
    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;

    public TimeSlot(LocalDate date, LocalTime start, LocalTime end) {
        if(end.isBefore(start)) {
            throw new IllegalArgumentException("Start time must be before end time !");
        }
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public LocalDate getDate() {
        return date;
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

    public DayOfWeek getDayOfWeek() {
        return DayOfWeek.from(date);
    }

    // No conflict only if other End is before my start
    // or other start is after my end
    public boolean isConflict(TimeSlot other) {
        if(!date.isEqual(other.date)) {
            return false;
        }
        if(isBefore(other) || isAfter(other)) {
            return false;
        }
        return true;
    }

    public boolean isBefore(TimeSlot other) {
        return other.end.isBefore(start) || other.end.equals(start);
    }

    public boolean isAfter(TimeSlot other) {
        return other.start.isAfter(end) || other.start.equals(end);
    }
}
