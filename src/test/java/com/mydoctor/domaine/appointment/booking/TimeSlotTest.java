package com.mydoctor.domaine.appointment.booking;

import com.mydoctor.domaine.appointment.booking.exception.IllegalArgumentException;
import com.mydoctor.domaine.appointment.booking.TimeSlot;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    @Test
    public void testCreationOfTimeSlotShouldRaiseExceptionWhenStartAfterEnd() {
        // Given
        LocalTime start = LocalTime.of(12, 30);
        LocalTime end = LocalTime.of(12, 0);

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> new TimeSlot(start, end));
    }

    @Test
    public void testDuration() {
        // Given
        LocalTime start = LocalTime.of(12, 30);
        LocalTime end = LocalTime.of(15, 40);
        TimeSlot timeSlot = new TimeSlot(start, end);

        // When
        Duration actualDuration = timeSlot.getDuration();

        // Then
        assertEquals(Duration.ofMinutes(190), actualDuration);
    }


    // IsBefore
    @Test
    public void testIsBeforeShouldSucceed() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(12, 30);
        LocalTime otherEnd = LocalTime.of(14, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isBefore(otherTimeSlot));
    }

    @Test
    public void testIsBeforeShouldFail() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(12, 30);
        LocalTime otherEnd = LocalTime.of(14, 50);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isBefore(otherTimeSlot));
    }

    // IsAfter
    @Test
    public void testIsAfterShouldSucceed() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(15, 30);
        LocalTime otherEnd = LocalTime.of(16, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isAfter(otherTimeSlot));
    }

    @Test
    public void testIsAfterShouldFail() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(16, 50);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isAfter(otherTimeSlot));
    }

    // IsStartEqually
    @Test
    public void testIsStartEquallyShouldSucceed() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 20);
        LocalTime otherEnd = LocalTime.of(16, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isStartEqually(otherTimeSlot));
    }

    @Test
    public void testIsStartEquallyShouldFail() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(16, 50);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isStartEqually(otherTimeSlot));
    }

    // IsEndEqually
    @Test
    public void testIsEndEquallyShouldSucceed() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 20);
        LocalTime otherEnd = LocalTime.of(15, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isEndEqually(otherTimeSlot));
    }

    @Test
    public void testIsEndEquallyShouldFail() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(16, 50);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isEndEqually(otherTimeSlot));
    }

    // IsStartBefore
    @Test
    public void testIsStartBeforeShouldSucceed() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 10);
        LocalTime otherEnd = LocalTime.of(15, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isStartBefore(otherTimeSlot));
    }

    @Test
    public void testIsStartBeforeShouldFail() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 20);
        LocalTime otherEnd = LocalTime.of(16, 50);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isStartBefore(otherTimeSlot));
    }

    // IsStartAfter
    @Test
    public void testIsStartAfterShouldSucceed() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(15, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isStartAfter(otherTimeSlot));
    }

    @Test
    public void testIsStartAfterShouldFail() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 10);
        LocalTime otherEnd = LocalTime.of(16, 50);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isStartAfter(otherTimeSlot));
    }

    // IsEndBefore
    @Test
    public void testIsEndBeforeShouldSucceed() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(15, 0);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isEndBefore(otherTimeSlot));
    }

    @Test
    public void testIsEndBeforeShouldFail() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 10);
        LocalTime otherEnd = LocalTime.of(15, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isEndBefore(otherTimeSlot));
    }

    // IsEndAfter
    @Test
    public void testIsEndAfterShouldSucceed() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(15, 30);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isEndAfter(otherTimeSlot));
    }

    @Test
    public void testIsEndAfterShouldFail() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 10);
        LocalTime otherEnd = LocalTime.of(15, 0);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isEndAfter(otherTimeSlot));
    }


    // IsEndBeforeMyStart
    @Test
    public void testIsEndBeforeMyStartShouldSucceed() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(12, 30);
        LocalTime otherEnd = LocalTime.of(14, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isEndBeforeMyStart(otherTimeSlot));
    }

    @Test
    public void testIsEndBeforeMyStartShouldFail() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(12, 20);
        LocalTime otherEnd = LocalTime.of(14, 21);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isEndBeforeMyStart(otherTimeSlot));
    }

    // IsStartAfterMyEnd
    @Test
    public void testIsStartAfterMyEndShouldSucceed() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(15, 10);
        LocalTime otherEnd = LocalTime.of(16, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isStartAfterMyEnd(otherTimeSlot));
    }

    @Test
    public void testIsStartAfterMyEndShouldFail() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(14, 50);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isStartAfterMyEnd(otherTimeSlot));
    }

    // IsInside
    @Test
    public void testIsInsideShouldSucceed() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(15, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isInside(otherTimeSlot));
    }

    @Test
    public void testIsInsideShouldFail() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 20);
        LocalTime otherEnd = LocalTime.of(15, 50);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isInside(otherTimeSlot));
    }

    // IsOutside
    @Test
    public void testIsOutsideShouldSucceed1() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 0);
        LocalTime otherEnd = LocalTime.of(14, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isOutside(otherTimeSlot));
    }

    @Test
    public void testIsOutsideShouldSucceed2() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(15, 20);
        LocalTime otherEnd = LocalTime.of(16, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isOutside(otherTimeSlot));
    }

    @Test
    public void testIsOutsideShouldFail1() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(15, 50);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isOutside(otherTimeSlot));
    }

    @Test
    public void testIsOutsideShouldFail2() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 20);
        LocalTime otherEnd = LocalTime.of(15, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isOutside(otherTimeSlot));
    }

    @Test
    public void testIsOutsideShouldFail3() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 0);
        LocalTime otherEnd = LocalTime.of(15, 50);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isOutside(otherTimeSlot));
    }

    //////////////

    @Test
    public void testNoConflictWhenOtherEndBeforeStart() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(12, 30);
        LocalTime otherEnd = LocalTime.of(14, 19);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isConflict(otherTimeSlot));
    }

    @Test
    public void testNoConflictWhenOtherStartAfterEnd() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(15, 30);
        LocalTime otherEnd = LocalTime.of(18, 10);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertFalse(timeSlot.isConflict(otherTimeSlot));
    }

    @Test
    public void testConflict1() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(13, 30);
        LocalTime otherEnd = LocalTime.of(14, 45);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isConflict(otherTimeSlot));
    }

    @Test
    public void testConflict2() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(14, 45);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isConflict(otherTimeSlot));
    }

    @Test
    public void testConflict3() {
        // Given
        LocalTime start = LocalTime.of(14, 20);
        LocalTime end = LocalTime.of(15, 10);
        LocalTime otherStart = LocalTime.of(14, 30);
        LocalTime otherEnd = LocalTime.of(16, 45);

        TimeSlot timeSlot = new TimeSlot(start, end);
        TimeSlot otherTimeSlot = new TimeSlot(otherStart, otherEnd);

        // When, Then
        assertTrue(timeSlot.isConflict(otherTimeSlot));
    }

}