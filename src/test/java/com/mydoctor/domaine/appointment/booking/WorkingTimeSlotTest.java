package com.mydoctor.domaine.appointment.booking;

import com.mydoctor.domaine.appointment.booking.exception.BookingException;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkingTimeSlotTest {

    /////// Booking //////

    // Successful Booking
    @Test
    void testBookShouldSucceedIfAvailableSlot1() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);
        BookableTimeInterval workingTimeSlot = new WorkingTimeInterval(start, end);

        LocalTime slotStart = LocalTime.of(8, 30);
        LocalTime slotEnd = LocalTime.of(9, 0);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        workingTimeSlot.book(timeSlot);

        // Then
        assertEquals(1, workingTimeSlot.getBookedSize());
    }

    @Test
    void testBookShouldSucceedIfAvailableSlot2() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeSlot = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(8, 30);
        LocalTime slotEnd = LocalTime.of(9, 0);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        workingTimeSlot.book(timeSlot);

        // Then
        assertEquals(3, workingTimeSlot.getBookedSize());
    }

    @Test
    void testBookShouldSucceedIfInsideAndNoConflict() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeSlot = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(9, 30);
        LocalTime slotEnd = LocalTime.of(12, 30);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        workingTimeSlot.book(timeSlot);

        // Then
        assertEquals(3, workingTimeSlot.getBookedSize());
    }

    // Failed Booking

    @Test
    void testBookShouldFailIfNotInside1() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeSlot = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(14, 30);
        LocalTime slotEnd = LocalTime.of(15, 0);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(BookingException.class, () -> workingTimeSlot.book(timeSlot));
    }

    @Test
    void testBookShouldFailIfNotInside2() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeSlot = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(6, 30);
        LocalTime slotEnd = LocalTime.of(   8, 0);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(BookingException.class, () -> workingTimeSlot.book(timeSlot));
    }

    @Test
    void testBookShouldFailIfNotInside3() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeSlot = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(11, 30);
        LocalTime slotEnd = LocalTime.of(   13, 0);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(BookingException.class, () -> workingTimeSlot.book(timeSlot));
    }

    @Test
    void testBookShouldFailIfConflictWithBooked() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeSlot = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(8, 0);
        LocalTime slotEnd = LocalTime.of(   8, 20);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(BookingException.class, () -> workingTimeSlot.book(timeSlot));
    }


    ////////////////////////////////////////////////////

    // IsConflictWithBooked

    @Test
    void testIsConflictWithBookedShouldSucceed() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeSlot = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(8, 20);
        LocalTime slotEnd = LocalTime.of(   8, 40);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        boolean actualIsConflict = workingTimeSlot.isConflictWithBooked(timeSlot);

        // Then
        assertTrue(actualIsConflict);
    }

    @Test
    void testIsConflictWithBookedShouldFail() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeSlot = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(8, 30);
        LocalTime slotEnd = LocalTime.of(   9, 0);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        boolean actualIsConflict = workingTimeSlot.isConflictWithBooked(timeSlot);

        // Then
        assertFalse(actualIsConflict);
    }

}