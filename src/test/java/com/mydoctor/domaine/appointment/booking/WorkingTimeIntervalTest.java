package com.mydoctor.domaine.appointment.booking;

import com.mydoctor.domaine.exception.IllegalArgumentException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkingTimeIntervalTest {

    // Constructor
    @Test
    void testConstructorShouldFailIfBookedIsNotInside() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(7,30), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);

        // When, Then
        assertThrows(IllegalArgumentException.class,
                () -> new WorkingTimeInterval(start, end, existingBooked));
    }

    @Test
    void testConstructorShouldFailIfBookedIsNotOrdered1() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(9,20), LocalTime.of(9,40));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2, existingSlot3);

        // When, Then
        assertThrows(IllegalArgumentException.class,
                () -> new WorkingTimeInterval(start, end, existingBooked));
    }

    @Test
    void testConstructorShouldFailIfBookedIsNotOrdered2() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(8,30), LocalTime.of(9,0));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2, existingSlot3);

        // When, Then
        assertThrows(IllegalArgumentException.class,
                () -> new WorkingTimeInterval(start, end, existingBooked));
    }

    /////// Booking //////

    // Successful Booking
    @Test
    void testBookShouldSucceedIfAvailableSlot1() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end);

        LocalTime slotStart = LocalTime.of(8, 30);
        LocalTime slotEnd = LocalTime.of(9, 0);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        workingTimeInterval.book(timeSlot);

        // Then
        assertEquals(1, workingTimeInterval.getBookedSize());
    }

    @Test
    void testBookShouldSucceedIfAvailableSlot2() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(8, 30);
        LocalTime slotEnd = LocalTime.of(9, 0);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        workingTimeInterval.book(timeSlot);

        // Then
        assertEquals(3, workingTimeInterval.getBookedSize());
    }

    @Test
    void testBookShouldSucceedIfInsideAndNoConflict() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(9, 30);
        LocalTime slotEnd = LocalTime.of(12, 30);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        workingTimeInterval.book(timeSlot);

        // Then
        assertEquals(3, workingTimeInterval.getBookedSize());
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
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(14, 30);
        LocalTime slotEnd = LocalTime.of(15, 0);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(BookingException.class, () -> workingTimeInterval.book(timeSlot));
    }

    @Test
    void testBookShouldFailIfNotInside2() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(6, 30);
        LocalTime slotEnd = LocalTime.of(   8, 0);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(BookingException.class, () -> workingTimeInterval.book(timeSlot));
    }

    @Test
    void testBookShouldFailIfNotInside3() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(11, 30);
        LocalTime slotEnd = LocalTime.of(   13, 0);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(BookingException.class, () -> workingTimeInterval.book(timeSlot));
    }

    @Test
    void testBookShouldFailIfConflictWithBooked() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(8, 0);
        LocalTime slotEnd = LocalTime.of(   8, 20);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(BookingException.class, () -> workingTimeInterval.book(timeSlot));
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
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(8, 20);
        LocalTime slotEnd = LocalTime.of(   8, 40);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        boolean actualIsConflict = workingTimeInterval.isConflictWithBooked(timeSlot);

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
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(8, 30);
        LocalTime slotEnd = LocalTime.of(   9, 0);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        boolean actualIsConflict = workingTimeInterval.isConflictWithBooked(timeSlot);

        // Then
        assertFalse(actualIsConflict);
    }


    ///// getAvailableSlotsSize

    // Test 1
    @Test
    void testGetAvailableSlotsSizeWhenNoBooking() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end);

        // When
        int actualAvailableSlotsSize = workingTimeInterval.getAvailableSlotsSize(Duration.ofMinutes(20));

        // Then
        assertEquals(13, actualAvailableSlotsSize);
    }

    // Test 2
    @Test
    void testGetAvailableSlotsSizeWhenOneBooking1() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 25);
        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,10), LocalTime.of(8,35));
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, List.of(existingSlot1));

        // When
        int actualAvailableSlotsSize = workingTimeInterval.getAvailableSlotsSize(Duration.ofMinutes(20));

        // Then
        assertEquals(11, actualAvailableSlotsSize);
    }

    // Test 3
    @Test
    void testGetAvailableSlotsSizeWhenOneBooking2() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(9, 25);
        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,10), LocalTime.of(9,5));
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, List.of(existingSlot1));

        // When
        int actualAvailableSlotsSize = workingTimeInterval.getAvailableSlotsSize(Duration.ofMinutes(25));

        // Then
        assertEquals(0, actualAvailableSlotsSize);
    }

    // Test4
    @Test
    void testGetAvailableSlotsSizeWhenMoreThanOneBooking() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(11, 25);
        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,10), LocalTime.of(8,27));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(8,45), LocalTime.of(9,5));
        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(9,19), LocalTime.of(9,37));
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, List.of(existingSlot1, existingSlot2, existingSlot3));

        // When
        int actualAvailableSlotsSize = workingTimeInterval.getAvailableSlotsSize(Duration.ofMinutes(13));

        // Then
        assertEquals(10, actualAvailableSlotsSize);
    }


    ///// getAvailableSlots

    // Test 1
    @Test
    void testGetAvailableSlotsWhenNoBooking() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end);

        // When
        List<TimeSlot> actualAvailableSlots = workingTimeInterval.getAvailableSlots(Duration.ofMinutes(25));

        // Then
        assertEquals(LocalTime.of(8, 25), actualAvailableSlots.get(0).getEnd());
        assertEquals(LocalTime.of(10, 5), actualAvailableSlots.get(5).getStart());
        assertEquals(10, actualAvailableSlots.size());
    }

    // Test 2
    @Test
    void testGetAvailableSlotsWhenOneBooking1() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(9,15), LocalTime.of(9,45));
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, List.of(existingSlot1));

        // When
        List<TimeSlot> actualAvailableSlots = workingTimeInterval.getAvailableSlots(Duration.ofMinutes(30));

        // Then
        assertEquals(LocalTime.of(9, 45), actualAvailableSlots.get(2).getStart());
        assertEquals(7, actualAvailableSlots.size());
    }

    // Test 3
    @Test
    void testGetAvailableSlotsWhenOneBooking2() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(9, 25);
        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,10), LocalTime.of(9,5));
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, List.of(existingSlot1));

        // When
        List<TimeSlot> actualAvailableSlots = workingTimeInterval.getAvailableSlots(Duration.ofMinutes(25));

        // Then
        assertEquals(0, actualAvailableSlots.size());
    }

    // Test4
    @Test
    void testGetAvailableSlotsWhenMoreThanOneBooking() {
        // Given
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(11, 25);
        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,10), LocalTime.of(8,27));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(8,45), LocalTime.of(9,5));
        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(9,19), LocalTime.of(9,37));
        BookableTimeInterval workingTimeInterval = new WorkingTimeInterval(start, end, List.of(existingSlot1, existingSlot2, existingSlot3));

        // When
        List<TimeSlot> actualAvailableSlots = workingTimeInterval.getAvailableSlots(Duration.ofMinutes(13));

        // Then
        assertEquals(LocalTime.of(9, 18), actualAvailableSlots.get(1).getEnd());
        assertEquals(LocalTime.of(10, 16), actualAvailableSlots.get(5).getStart());
        assertEquals(10, actualAvailableSlots.size());
    }

}