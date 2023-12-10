package com.mydoctor.domaine.appointment;

import com.mydoctor.domaine.appointment.exception.TimeSlotNotBookedException;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkingTimeSlotTest {

    @Test
    void testBookShouldSucceedIfAvailableSlot1() {
        // Given
        LocalTime start = LocalTime.of(8, 00);
        LocalTime end = LocalTime.of(12, 30);
        WorkingTimeSlot workingTimeSlot = new WorkingTimeSlot(start, end);

        LocalTime slotStart = LocalTime.of(8, 30);
        LocalTime slotEnd = LocalTime.of(9, 00);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        workingTimeSlot.book(timeSlot);

        // Then
        assertEquals(1, workingTimeSlot.getBookedSize());
    }

    @Test
    void testBookShouldSucceedIfAvailableSlot2() {
        // Given
        LocalTime start = LocalTime.of(8, 00);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,00), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,00), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeSlot workingTimeSlot = new WorkingTimeSlot(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(8, 30);
        LocalTime slotEnd = LocalTime.of(9, 00);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When
        workingTimeSlot.book(timeSlot);

        // Then
        assertEquals(3, workingTimeSlot.getBookedSize());
    }

    @Test
    void testBookShouldFailIfOutside1() {
        // Given
        LocalTime start = LocalTime.of(8, 00);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,00), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,00), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeSlot workingTimeSlot = new WorkingTimeSlot(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(14, 30);
        LocalTime slotEnd = LocalTime.of(15, 00);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(TimeSlotNotBookedException.class, () -> workingTimeSlot.book(timeSlot));
    }

    @Test
    void testBookShouldFailIfOutside2() {
        // Given
        LocalTime start = LocalTime.of(8, 00);
        LocalTime end = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,00), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,00), LocalTime.of(9,30));
        List<TimeSlot> existingBooked = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeSlot workingTimeSlot = new WorkingTimeSlot(start, end, existingBooked);

        LocalTime slotStart = LocalTime.of(6, 30);
        LocalTime slotEnd = LocalTime.of(   8, 00);
        TimeSlot timeSlot = new TimeSlot(slotStart, slotEnd);

        // When, Then
        assertThrows(TimeSlotNotBookedException.class, () -> workingTimeSlot.book(timeSlot));
    }

}