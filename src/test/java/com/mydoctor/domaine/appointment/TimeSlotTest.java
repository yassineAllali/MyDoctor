package com.mydoctor.domaine.appointment;

import com.mydoctor.domaine.appointment.exception.CalendarException;
import com.mydoctor.domaine.appointment.exception.IllegalArgumentException;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    @Test
    public void testCreationOfTimeSlotShouldRaiseExceptionWhenStartAfterEnd() {
        // Given
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.of(12, 30);
        LocalTime end = LocalTime.of(12, 0);

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> new TimeSlotTestImpl(date, start, end));
    }

    @Test
    public void testDuration() {
        // Given
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.of(12, 30);
        LocalTime end = LocalTime.of(15, 40);
        TimeSlot timeSlot = new TimeSlotTestImpl(date, start, end);

        // When
        Duration actualDuration = timeSlot.getDuration();

        // Then
        assertTrue(Duration.ofMinutes(190).equals(actualDuration));
    }

    @Test
    public void testDayOfWeek() {
        // Given
        LocalDate date = LocalDate.of(2023, 12, 8);
        LocalTime start = LocalTime.of(12, 30);
        LocalTime end = LocalTime.of(15, 40);
        TimeSlot timeSlot = new TimeSlotTestImpl(date, start, end);

        // When
        DayOfWeek actualDayOfWeek = timeSlot.getDayOfWeek();

        // Then
        assertEquals(DayOfWeek.FRIDAY, actualDayOfWeek);
    }

    @Test
    public void testNoConflictWhenOtherEndBeforeStart() {
        // Given
        LocalDate date = LocalDate.of(2023, 12, 8);

        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(12, 30);
        LocalTime otherEnd = LocalTime.of(13, 10);

        TimeSlot timeSlot = new TimeSlotTestImpl(date, start, end);
        TimeSlot otherTimeSlot = new TimeSlotTestImpl(date, otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isConflict(otherTimeSlot));
    }

    @Test
    public void testNoConflictWhenOtherStartAfterEnd() {
        // Given
        LocalDate date = LocalDate.of(2023, 12, 8);

        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(15, 30);
        LocalTime otherEnd = LocalTime.of(18, 10);

        TimeSlot timeSlot = new TimeSlotTestImpl(date, start, end);
        TimeSlot otherTimeSlot = new TimeSlotTestImpl(date, otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isConflict(otherTimeSlot));
    }

    @Test
    public void testNoConflictWhenDifferentDates() {
        // Given
        LocalDate date1 = LocalDate.of(2023, 12, 8);
        LocalDate date2 = LocalDate.of(2023, 12, 3);

        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(13, 30);
        LocalTime otherEnd = LocalTime.of(14, 45);

        TimeSlot timeSlot = new TimeSlotTestImpl(date1, start, end);
        TimeSlot otherTimeSlot = new TimeSlotTestImpl(date2, otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isConflict(otherTimeSlot));
    }

    @Test
    public void testConflict1() {
        // Given
        LocalDate date = LocalDate.of(2023, 12, 8);
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(13, 30);
        LocalTime otherEnd = LocalTime.of(14, 45);

        TimeSlot timeSlot = new TimeSlotTestImpl(date, start, end);
        TimeSlot otherTimeSlot = new TimeSlotTestImpl(date, otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isConflict(otherTimeSlot));
    }

    @Test
    public void testConflict2() {
        // Given
        LocalDate date = LocalDate.of(2023, 12, 8);

        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(14, 45);

        TimeSlot timeSlot = new TimeSlotTestImpl(date, start, end);
        TimeSlot otherTimeSlot = new TimeSlotTestImpl(date, otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isConflict(otherTimeSlot));
    }

    @Test
    public void testConflict3() {
        // Given
        LocalDate date = LocalDate.of(2023, 12, 8);

        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(16, 45);

        TimeSlot timeSlot = new TimeSlotTestImpl(date, start, end);
        TimeSlot otherTimeSlot = new TimeSlotTestImpl(date, otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isConflict(otherTimeSlot));
    }


    private static class TimeSlotTestImpl extends TimeSlot {
        public TimeSlotTestImpl(LocalDate date, LocalTime start, LocalTime end) {
            super(date, start, end);
        }
    }

}