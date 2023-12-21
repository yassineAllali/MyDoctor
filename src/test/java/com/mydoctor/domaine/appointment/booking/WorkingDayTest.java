package com.mydoctor.domaine.appointment.booking;

import com.mydoctor.domaine.appointment.booking.exception.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkingDayTest {

    // BookedSize
    @Test
    public void testBookedSize() {
        // Given

        // Working TimeSlot 1
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2
        LocalTime workingStart2 = LocalTime.of(8, 0);
        LocalTime workingEnd2 = LocalTime.of(12, 30);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working Day
        LocalDate date = LocalDate.of(2023, 12,10);
        BookableTimeInterval workingDay = new WorkingDay(date, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // When
        int actualBookedSize = workingDay.getBookedSize();

        // Then
        assertEquals(4, actualBookedSize);
    }

    // IsConflictWithBooked

    @Test
    public void testConflictWithBookedShouldSucceed() {
        // Given

        // Working TimeSlot 1
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2
        LocalTime workingStart2 = LocalTime.of(14, 0);
        LocalTime workingEnd2 = LocalTime.of(18, 30);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(14,0), LocalTime.of(15,30));
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,30), LocalTime.of(16,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working Day
        LocalDate date = LocalDate.of(2023, 12,10);
        BookableTimeInterval workingDay = new WorkingDay(date, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // TimeSlot
        LocalTime slotStart = LocalTime.of(14, 30);
        LocalTime slotEnd = LocalTime.of(15, 40);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        boolean actualIsConflict = workingDay.isConflictWithBooked(timeSlot);

        // Then
        assertTrue(actualIsConflict);
    }

    @Test
    public void testConflictWithBookedShouldFail() {
        // Given

        // Working TimeSlot 1
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2
        LocalTime workingStart2 = LocalTime.of(14, 0);
        LocalTime workingEnd2 = LocalTime.of(18, 30);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(14,0), LocalTime.of(15,30));
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,30), LocalTime.of(16,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working Day
        LocalDate date = LocalDate.of(2023, 12,10);
        BookableTimeInterval workingDay = new WorkingDay(date, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // TimeSlot
        LocalTime slotStart = LocalTime.of(16, 30);
        LocalTime slotEnd = LocalTime.of(18, 40);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        boolean actualIsConflict = workingDay.isConflictWithBooked(timeSlot);

        // Then
        assertFalse(actualIsConflict);
    }

    // IsInside

    @Test
    public void testInsideShouldSucceed() {
        // Given

        // Working TimeSlot 1
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2
        LocalTime workingStart2 = LocalTime.of(14, 0);
        LocalTime workingEnd2 = LocalTime.of(18, 30);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(14,0), LocalTime.of(15,30));
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,30), LocalTime.of(16,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working Day
        LocalDate date = LocalDate.of(2023, 12,10);
        BookableTimeInterval workingDay = new WorkingDay(date, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // TimeSlot
        LocalTime slotStart = LocalTime.of(8, 20);
        LocalTime slotEnd = LocalTime.of(9, 15);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        boolean actualIsConflict = workingDay.isInside(timeSlot);

        // Then
        assertTrue(actualIsConflict);
    }

    @Test
    public void testInsideShouldFail() {
        // Given

        // Working TimeSlot 1
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2
        LocalTime workingStart2 = LocalTime.of(14, 0);
        LocalTime workingEnd2 = LocalTime.of(18, 30);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(14,0), LocalTime.of(15,30));
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,30), LocalTime.of(16,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working Day
        LocalDate date = LocalDate.of(2023, 12,10);
        BookableTimeInterval workingDay = new WorkingDay(date, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // TimeSlot
        LocalTime slotStart = LocalTime.of(9, 10);
        LocalTime slotEnd = LocalTime.of(14, 40);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        boolean actualIsConflict = workingDay.isInside(timeSlot);

        // Then
        assertFalse(actualIsConflict);
    }

    //// Book

    // Success
    @Test
    public void testBookShouldSucceedIfInsideAndNoConflict() {
        // Given

        // Working TimeSlot 1
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2
        LocalTime workingStart2 = LocalTime.of(14, 0);
        LocalTime workingEnd2 = LocalTime.of(18, 30);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(14,0), LocalTime.of(14,30));
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,30), LocalTime.of(16,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working Day
        LocalDate date = LocalDate.of(2023, 12,10);
        BookableTimeInterval workingDay = new WorkingDay(date, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // TimeSlot
        LocalTime slotStart = LocalTime.of(14, 30);
        LocalTime slotEnd = LocalTime.of(15, 30);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        workingDay.book(timeSlot);

        // Then
        assertEquals(5, workingDay.getBookedSize());
    }

    // Failed
    @Test
    public void testBookShouldFailIfNotInside() {
        // Given

        // Working TimeSlot 1
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2
        LocalTime workingStart2 = LocalTime.of(14, 0);
        LocalTime workingEnd2 = LocalTime.of(18, 30);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(14,0), LocalTime.of(15,30));
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,30), LocalTime.of(16,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working Day
        LocalDate date = LocalDate.of(2023, 12,10);
        BookableTimeInterval workingDay = new WorkingDay(date, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // TimeSlot
        LocalTime slotStart = LocalTime.of(12, 30);
        LocalTime slotEnd = LocalTime.of(13, 40);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(BookingException.class,() -> workingDay.book(timeSlot));
    }

    @Test
    public void testBookShouldFailIfConflict() {
        // Given

        // Working TimeSlot 1
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2
        LocalTime workingStart2 = LocalTime.of(14, 0);
        LocalTime workingEnd2 = LocalTime.of(18, 30);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(14,0), LocalTime.of(15,30));
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,30), LocalTime.of(16,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working Day
        LocalDate date = LocalDate.of(2023, 12,10);
        BookableTimeInterval workingDay = new WorkingDay(date, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // TimeSlot
        LocalTime slotStart = LocalTime.of(8, 20);
        LocalTime slotEnd = LocalTime.of(9, 40);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(BookingException.class,() -> workingDay.book(timeSlot));
    }

    // TestGetAvailableSlots

    // Test 1 :
    @Test
    public void testGetAvailableSlots() {
        // Given

        // Working TimeSlot 1
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2
        LocalTime workingStart2 = LocalTime.of(14, 0);
        LocalTime workingEnd2 = LocalTime.of(18, 30);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(14,0), LocalTime.of(15,30));
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,30), LocalTime.of(16,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working Day
        LocalDate date = LocalDate.of(2023, 12,10);
        BookableTimeInterval workingDay = new WorkingDay(date, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // TimeSlot
        LocalTime slotStart = LocalTime.of(8, 20);
        LocalTime slotEnd = LocalTime.of(9, 40);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(BookingException.class,() -> workingDay.book(timeSlot));
    }

}