package com.mydoctor.domaine.appointment.booking;

import com.mydoctor.domaine.appointment.booking.exception.BookingException;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkingPeriodTest {

    // GetBookedSize

    @Test
    public void testGetBookedSize() {
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

        // Working TimeSlot 3
        LocalTime workingStart3 = LocalTime.of(8, 0);
        LocalTime workingEnd3 = LocalTime.of(12, 30);

        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot5, existingSlot6);
        WorkingTimeInterval workingTimeSlot3 = new WorkingTimeInterval(workingStart3, workingEnd3, existingBooked3);

        // Working TimeSlot 4
        LocalTime workingStart4 = LocalTime.of(8, 0);
        LocalTime workingEnd4 = LocalTime.of(12, 30);

        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(9,30), LocalTime.of(10,30));
        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot7, existingSlot8, existingSlot9);
        WorkingTimeInterval workingTimeSlot4 = new WorkingTimeInterval(workingStart4, workingEnd4, existingBooked4);

        // Working Day 1
        LocalDate date1 = LocalDate.of(2023, 12,10);
        WorkingDay workingDay1 = new WorkingDay(date1, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Working Day 2
        LocalDate date2 = LocalDate.of(2023, 12,10);
        WorkingDay workingDay2 = new WorkingDay(date2, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 13);
        LocalDate workingEnd = LocalDate.of(2023, 12, 16);
        Bookable workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2));

        // When
        int actualBookedSize = workingPeriod.getBookedSize();

        // Then
        assertEquals(9, actualBookedSize);
    }

    // IsInside

    // Succeed
    @Test
    public void testIsInsideShouldSucceed() {
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
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working TimeSlot 3
        LocalTime workingStart3 = LocalTime.of(8, 0);
        LocalTime workingEnd3 = LocalTime.of(12, 30);

        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot5, existingSlot6);
        WorkingTimeInterval workingTimeSlot3 = new WorkingTimeInterval(workingStart3, workingEnd3, existingBooked3);

        // Working TimeSlot 4
        LocalTime workingStart4 = LocalTime.of(14, 0);
        LocalTime workingEnd4 = LocalTime.of(17, 30);

        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(16,0), LocalTime.of(16,30));
        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(17,30), LocalTime.of(17,30));
        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot7, existingSlot8, existingSlot9);
        WorkingTimeInterval workingTimeSlot4 = new WorkingTimeInterval(workingStart4, workingEnd4, existingBooked4);

        // Working Day 1
        LocalDate date1 = LocalDate.of(2023, 12,17);
        WorkingDay workingDay1 = new WorkingDay(date1, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Working Day 2
        LocalDate date2 = LocalDate.of(2023, 12,18);
        WorkingDay workingDay2 = new WorkingDay(date2, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 17);
        LocalDate workingEnd = LocalDate.of(2023, 12, 18);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2));

        // Given TimeSlot
        LocalTime slotStart = LocalTime.of(14, 30);
        LocalTime slotEnd = LocalTime.of(15, 40);
        TimeSlot givenTimeSlot = new TimeSlot(slotStart, slotEnd);

        // Given Date
        LocalDate bookingDate = LocalDate.of(2023, 12, 18);

        // When
        boolean actualIsInside = workingPeriod.isInside(bookingDate, givenTimeSlot);

        // Then
        assertTrue(actualIsInside);
    }

    // Fail

    @Test
    public void testIsInsideShouldFailIfDifferentDate() {
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
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working TimeSlot 3
        LocalTime workingStart3 = LocalTime.of(8, 0);
        LocalTime workingEnd3 = LocalTime.of(12, 30);

        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot5, existingSlot6);
        WorkingTimeInterval workingTimeSlot3 = new WorkingTimeInterval(workingStart3, workingEnd3, existingBooked3);

        // Working TimeSlot 4
        LocalTime workingStart4 = LocalTime.of(14, 0);
        LocalTime workingEnd4 = LocalTime.of(17, 30);

        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(16,0), LocalTime.of(16,30));
        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(17,30), LocalTime.of(17,30));
        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot7, existingSlot8, existingSlot9);
        WorkingTimeInterval workingTimeSlot4 = new WorkingTimeInterval(workingStart4, workingEnd4, existingBooked4);

        // Working Day 1
        LocalDate date1 = LocalDate.of(2023, 12,17);
        WorkingDay workingDay1 = new WorkingDay(date1, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Working Day 2
        LocalDate date2 = LocalDate.of(2023, 12,18);
        WorkingDay workingDay2 = new WorkingDay(date2, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 17);
        LocalDate workingEnd = LocalDate.of(2023, 12, 18);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2));

        // Given TimeSlot
        LocalTime slotStart = LocalTime.of(14, 30);
        LocalTime slotEnd = LocalTime.of(15, 40);
        TimeSlot givenTimeSlot = new TimeSlot(slotStart, slotEnd);

        // Given Date
        LocalDate bookingDate = LocalDate.of(2023, 12, 20);

        // When
        boolean actualIsInside = workingPeriod.isInside(bookingDate, givenTimeSlot);

        // Then
        assertFalse(actualIsInside);
    }

    @Test
    public void testIsInsideShouldFailIfDateExistsButNotInsideDays() {
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
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working TimeSlot 3
        LocalTime workingStart3 = LocalTime.of(8, 0);
        LocalTime workingEnd3 = LocalTime.of(12, 30);

        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot5, existingSlot6);
        WorkingTimeInterval workingTimeSlot3 = new WorkingTimeInterval(workingStart3, workingEnd3, existingBooked3);

        // Working TimeSlot 4
        LocalTime workingStart4 = LocalTime.of(14, 0);
        LocalTime workingEnd4 = LocalTime.of(17, 30);

        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(16,0), LocalTime.of(16,30));
        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(17,30), LocalTime.of(17,30));
        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot7, existingSlot8, existingSlot9);
        WorkingTimeInterval workingTimeSlot4 = new WorkingTimeInterval(workingStart4, workingEnd4, existingBooked4);

        // Working Day 1
        LocalDate date1 = LocalDate.of(2023, 12,17);
        WorkingDay workingDay1 = new WorkingDay(date1, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Working Day 2
        LocalDate date2 = LocalDate.of(2023, 12,18);
        WorkingDay workingDay2 = new WorkingDay(date2, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 17);
        LocalDate workingEnd = LocalDate.of(2023, 12, 18);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2));

        // Given TimeSlot
        LocalTime slotStart = LocalTime.of(13, 30);
        LocalTime slotEnd = LocalTime.of(15, 40);
        TimeSlot givenTimeSlot = new TimeSlot(slotStart, slotEnd);

        // Given Date
        LocalDate bookingDate = LocalDate.of(2023, 12, 18);

        // When
        boolean actualIsInside = workingPeriod.isInside(bookingDate, givenTimeSlot);

        // Then
        assertFalse(actualIsInside);
    }

    // IsConflictWithBooked

    // Success

    @Test
    public void testIsConflictWithBookedShouldSucceed() {
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
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working TimeSlot 3
        LocalTime workingStart3 = LocalTime.of(8, 0);
        LocalTime workingEnd3 = LocalTime.of(12, 30);

        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot5, existingSlot6);
        WorkingTimeInterval workingTimeSlot3 = new WorkingTimeInterval(workingStart3, workingEnd3, existingBooked3);

        // Working TimeSlot 4
        LocalTime workingStart4 = LocalTime.of(14, 0);
        LocalTime workingEnd4 = LocalTime.of(17, 30);

        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(16,0), LocalTime.of(16,30));
        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(17,30), LocalTime.of(17,30));
        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot7, existingSlot8, existingSlot9);
        WorkingTimeInterval workingTimeSlot4 = new WorkingTimeInterval(workingStart4, workingEnd4, existingBooked4);

        // Working Day 1
        LocalDate date1 = LocalDate.of(2023, 12,17);
        WorkingDay workingDay1 = new WorkingDay(date1, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Working Day 2
        LocalDate date2 = LocalDate.of(2023, 12,18);
        WorkingDay workingDay2 = new WorkingDay(date2, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 17);
        LocalDate workingEnd = LocalDate.of(2023, 12, 18);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2));

        // Given TimeSlot
        LocalTime slotStart = LocalTime.of(16, 20);
        LocalTime slotEnd = LocalTime.of(16, 40);
        TimeSlot givenTimeSlot = new TimeSlot(slotStart, slotEnd);

        // Given Date
        LocalDate bookingDate = LocalDate.of(2023, 12, 18);

        // When
        boolean actualIsConflict = workingPeriod.isConflictWithBooked(bookingDate, givenTimeSlot);

        // Then
        assertTrue(actualIsConflict);
    }

    // Fail

    @Test
    public void testIsConflictWithBookedShouldFailIfDifferentDate() {
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
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working TimeSlot 3
        LocalTime workingStart3 = LocalTime.of(8, 0);
        LocalTime workingEnd3 = LocalTime.of(12, 30);

        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot5, existingSlot6);
        WorkingTimeInterval workingTimeSlot3 = new WorkingTimeInterval(workingStart3, workingEnd3, existingBooked3);

        // Working TimeSlot 4
        LocalTime workingStart4 = LocalTime.of(14, 0);
        LocalTime workingEnd4 = LocalTime.of(17, 30);

        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(16,0), LocalTime.of(16,30));
        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(17,30), LocalTime.of(17,30));
        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot7, existingSlot8, existingSlot9);
        WorkingTimeInterval workingTimeSlot4 = new WorkingTimeInterval(workingStart4, workingEnd4, existingBooked4);

        // Working Day 1
        LocalDate date1 = LocalDate.of(2023, 12,17);
        WorkingDay workingDay1 = new WorkingDay(date1, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Working Day 2
        LocalDate date2 = LocalDate.of(2023, 12,18);
        WorkingDay workingDay2 = new WorkingDay(date2, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 17);
        LocalDate workingEnd = LocalDate.of(2023, 12, 18);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2));

        // Given TimeSlot
        LocalTime slotStart = LocalTime.of(16, 20);
        LocalTime slotEnd = LocalTime.of(16, 40);
        TimeSlot givenTimeSlot = new TimeSlot(slotStart, slotEnd);

        // Given Date
        LocalDate bookingDate = LocalDate.of(2023, 12, 20);

        // When
        boolean actualIsConflict = workingPeriod.isConflictWithBooked(bookingDate, givenTimeSlot);

        // Then
        assertFalse(actualIsConflict);
    }

    @Test
    public void testIsConflictWithBookedShouldFailIfDateExistButNoConflict() {
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
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working TimeSlot 3
        LocalTime workingStart3 = LocalTime.of(8, 0);
        LocalTime workingEnd3 = LocalTime.of(12, 30);

        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot5, existingSlot6);
        WorkingTimeInterval workingTimeSlot3 = new WorkingTimeInterval(workingStart3, workingEnd3, existingBooked3);

        // Working TimeSlot 4
        LocalTime workingStart4 = LocalTime.of(14, 0);
        LocalTime workingEnd4 = LocalTime.of(17, 30);

        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(16,0), LocalTime.of(16,30));
        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(17,30), LocalTime.of(17,30));
        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot7, existingSlot8, existingSlot9);
        WorkingTimeInterval workingTimeSlot4 = new WorkingTimeInterval(workingStart4, workingEnd4, existingBooked4);

        // Working Day 1
        LocalDate date1 = LocalDate.of(2023, 12,17);
        WorkingDay workingDay1 = new WorkingDay(date1, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Working Day 2
        LocalDate date2 = LocalDate.of(2023, 12,18);
        WorkingDay workingDay2 = new WorkingDay(date2, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 17);
        LocalDate workingEnd = LocalDate.of(2023, 12, 18);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2));

        // Given TimeSlot
        LocalTime slotStart = LocalTime.of(14, 30);
        LocalTime slotEnd = LocalTime.of(15, 0);
        TimeSlot givenTimeSlot = new TimeSlot(slotStart, slotEnd);

        // Given Date
        LocalDate bookingDate = LocalDate.of(2023, 12, 17);

        // When
        boolean actualIsConflict = workingPeriod.isConflictWithBooked(bookingDate, givenTimeSlot);

        // Then
        assertFalse(actualIsConflict);
    }



    // Book

    // Succeed
    @Test
    public void testBookShouldSucceed() {

        // Given

        // Working TimeSlot 1 : (8:00 -> 12:30)
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2 : (14:00 -> 18:00)
        LocalTime workingStart2 = LocalTime.of(14, 0);
        LocalTime workingEnd2 = LocalTime.of(18, 0);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(14, 0), LocalTime.of(14, 15)); // 15 minutes duration
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15, 0), LocalTime.of(15, 20)); // 20 minutes duration
        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(16, 0), LocalTime.of(16, 30)); // 30 minutes duration

        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4, existingSlot5);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working TimeSlot 3 : (8:00 -> 12:00)
        LocalTime workingStart3 = LocalTime.of(8, 0);
        LocalTime workingEnd3 = LocalTime.of(12, 0);

        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(8, 0), LocalTime.of(8, 15)); // 15 minutes duration
        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(9, 20)); // 20 minutes duration
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 30)); // 30 minutes duration

        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot6, existingSlot7, existingSlot8);
        WorkingTimeInterval workingTimeSlot3 = new WorkingTimeInterval(workingStart3, workingEnd3, existingBooked3);


        // Working TimeSlot 4 : (14:30 -> 17:30)
        LocalTime workingStart4 = LocalTime.of(14, 30);
        LocalTime workingEnd4 = LocalTime.of(17, 30);

        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(14, 30), LocalTime.of(14, 45)); // 15 minutes duration
        TimeSlot existingSlot10 = new TimeSlot(LocalTime.of(15, 0), LocalTime.of(15, 25)); // 25 minutes duration
        TimeSlot existingSlot11 = new TimeSlot(LocalTime.of(16, 0), LocalTime.of(16, 30)); // 30 minutes duration

        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot9, existingSlot10, existingSlot11);
        WorkingTimeInterval workingTimeSlot4 = new WorkingTimeInterval(workingStart4, workingEnd4, existingBooked4);

        // Working TimeSlot 5 : (9:00 -> 12:30)
        LocalTime workingStart5 = LocalTime.of(9, 0);
        LocalTime workingEnd5 = LocalTime.of(12, 30);
        TimeSlot existingSlot12 = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(9, 15));  // 15 minutes
        TimeSlot existingSlot13 = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 20)); // 20 minutes
        TimeSlot existingSlot14 = new TimeSlot(LocalTime.of(11, 0), LocalTime.of(11, 30)); // 30 minutes
        List<TimeSlot> existingBooked5 = Arrays.asList(existingSlot12, existingSlot13, existingSlot14);
        WorkingTimeInterval workingTimeSlot5 = new WorkingTimeInterval(workingStart5, workingEnd5, existingBooked5);

        // Working TimeSlot 6 : (14:30 to 18:30)
        LocalTime workingStart6 = LocalTime.of(14, 30);
        LocalTime workingEnd6 = LocalTime.of(18, 30);
        TimeSlot existingSlot15 = new TimeSlot(LocalTime.of(14, 30), LocalTime.of(14, 45)); // 15 minutes
        TimeSlot existingSlot16 = new TimeSlot(LocalTime.of(15, 30), LocalTime.of(15, 50)); // 20 minutes
        TimeSlot existingSlot17 = new TimeSlot(LocalTime.of(16, 30), LocalTime.of(17, 0));  // 30 minutes
        List<TimeSlot> existingBooked6 = Arrays.asList(existingSlot15, existingSlot16, existingSlot17);
        WorkingTimeInterval workingTimeSlot6 = new WorkingTimeInterval(workingStart6, workingEnd6, existingBooked6);


        // First Working Day with WorkingTimeSlot1 and WorkingTimeSlot2
        LocalDate dateForFirstWorkingDay = LocalDate.of(2023, 12, 17); // Example date
        WorkingDay workingDay1 = new WorkingDay(dateForFirstWorkingDay, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Second Working Day with WorkingTimeSlot3 and WorkingTimeSlot4
        LocalDate dateForSecondWorkingDay = LocalDate.of(2023, 12, 18); // Another example date
        WorkingDay workingDay2 = new WorkingDay(dateForSecondWorkingDay, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Third Working Day with workingTimeSlot5 and workingTimeSlot6
        LocalDate dateForThirdWorkingDay = LocalDate.of(2023, 12, 19); // Example date
        WorkingDay workingDay3 = new WorkingDay(dateForThirdWorkingDay, Arrays.asList(workingTimeSlot5, workingTimeSlot6));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 17);
        LocalDate workingEnd = LocalDate.of(2023, 12, 20);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2, workingDay3));


        // Given TimeSlot
        LocalTime slotStart = LocalTime.of(14, 45);
        LocalTime slotEnd = LocalTime.of(15, 0);
        TimeSlot givenTimeSlot = new TimeSlot(slotStart, slotEnd);

        // Given Date
        LocalDate bookingDate = LocalDate.of(2023, 12, 18);

        // When
        workingPeriod.book(bookingDate, givenTimeSlot);
        int actualBookedSize = workingPeriod.getBookedSize();

        // Then
        assertEquals(18, actualBookedSize);
        assertTrue(workingPeriod.isConflictWithBooked(bookingDate, new TimeSlot(LocalTime.of(14, 40), LocalTime.of(15, 20))));

    }

    // Fail
    @Test
    public void testBookShouldFailIfNotInside() {

        // Given

        // Working TimeSlot 1 : (8:00 -> 12:30)
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2 : (14:00 -> 18:00)
        LocalTime workingStart2 = LocalTime.of(14, 0);
        LocalTime workingEnd2 = LocalTime.of(18, 0);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(14, 0), LocalTime.of(14, 15)); // 15 minutes duration
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15, 0), LocalTime.of(15, 20)); // 20 minutes duration
        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(16, 0), LocalTime.of(16, 30)); // 30 minutes duration

        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4, existingSlot5);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working TimeSlot 3 : (8:00 -> 12:00)
        LocalTime workingStart3 = LocalTime.of(8, 0);
        LocalTime workingEnd3 = LocalTime.of(12, 0);

        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(8, 0), LocalTime.of(8, 15)); // 15 minutes duration
        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(9, 20)); // 20 minutes duration
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 30)); // 30 minutes duration

        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot6, existingSlot7, existingSlot8);
        WorkingTimeInterval workingTimeSlot3 = new WorkingTimeInterval(workingStart3, workingEnd3, existingBooked3);


        // Working TimeSlot 4 : (14:30 -> 17:30)
        LocalTime workingStart4 = LocalTime.of(14, 30);
        LocalTime workingEnd4 = LocalTime.of(17, 30);

        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(14, 30), LocalTime.of(14, 45)); // 15 minutes duration
        TimeSlot existingSlot10 = new TimeSlot(LocalTime.of(15, 0), LocalTime.of(15, 25)); // 25 minutes duration
        TimeSlot existingSlot11 = new TimeSlot(LocalTime.of(16, 0), LocalTime.of(16, 30)); // 30 minutes duration

        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot9, existingSlot10, existingSlot11);
        WorkingTimeInterval workingTimeSlot4 = new WorkingTimeInterval(workingStart4, workingEnd4, existingBooked4);

        // Working TimeSlot 5 : (9:00 -> 12:30)
        LocalTime workingStart5 = LocalTime.of(9, 0);
        LocalTime workingEnd5 = LocalTime.of(12, 30);
        TimeSlot existingSlot12 = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(9, 15));  // 15 minutes
        TimeSlot existingSlot13 = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 20)); // 20 minutes
        TimeSlot existingSlot14 = new TimeSlot(LocalTime.of(11, 0), LocalTime.of(11, 30)); // 30 minutes
        List<TimeSlot> existingBooked5 = Arrays.asList(existingSlot12, existingSlot13, existingSlot14);
        WorkingTimeInterval workingTimeSlot5 = new WorkingTimeInterval(workingStart5, workingEnd5, existingBooked5);

        // Working TimeSlot 6 : (14:30 to 18:30)
        LocalTime workingStart6 = LocalTime.of(14, 30);
        LocalTime workingEnd6 = LocalTime.of(18, 30);
        TimeSlot existingSlot15 = new TimeSlot(LocalTime.of(14, 30), LocalTime.of(14, 45)); // 15 minutes
        TimeSlot existingSlot16 = new TimeSlot(LocalTime.of(15, 30), LocalTime.of(15, 50)); // 20 minutes
        TimeSlot existingSlot17 = new TimeSlot(LocalTime.of(16, 30), LocalTime.of(17, 0));  // 30 minutes
        List<TimeSlot> existingBooked6 = Arrays.asList(existingSlot15, existingSlot16, existingSlot17);
        WorkingTimeInterval workingTimeSlot6 = new WorkingTimeInterval(workingStart6, workingEnd6, existingBooked6);


        // First Working Day with WorkingTimeSlot1 and WorkingTimeSlot2
        LocalDate dateForFirstWorkingDay = LocalDate.of(2023, 12, 17); // Example date
        WorkingDay workingDay1 = new WorkingDay(dateForFirstWorkingDay, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Second Working Day with WorkingTimeSlot3 and WorkingTimeSlot4
        LocalDate dateForSecondWorkingDay = LocalDate.of(2023, 12, 18); // Another example date
        WorkingDay workingDay2 = new WorkingDay(dateForSecondWorkingDay, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Third Working Day with workingTimeSlot5 and workingTimeSlot6
        LocalDate dateForThirdWorkingDay = LocalDate.of(2023, 12, 19); // Example date
        WorkingDay workingDay3 = new WorkingDay(dateForThirdWorkingDay, Arrays.asList(workingTimeSlot5, workingTimeSlot6));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 17);
        LocalDate workingEnd = LocalDate.of(2023, 12, 20);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2, workingDay3));


        // Given TimeSlot
        LocalTime slotStart = LocalTime.of(14, 45);
        LocalTime slotEnd = LocalTime.of(15, 0);
        TimeSlot givenTimeSlot = new TimeSlot(slotStart, slotEnd);

        // Given Date
        LocalDate bookingDate = LocalDate.of(2023, 12, 20);

        // When, Then
        assertThrows(BookingException.class, () -> workingPeriod.book(bookingDate, givenTimeSlot));

    }

    @Test
    public void testBookShouldFailIfConflict() {

        // Given

        // Working TimeSlot 1 : (8:00 -> 12:30)
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2 : (14:00 -> 18:00)
        LocalTime workingStart2 = LocalTime.of(14, 0);
        LocalTime workingEnd2 = LocalTime.of(18, 0);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(14, 0), LocalTime.of(14, 15)); // 15 minutes duration
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15, 0), LocalTime.of(15, 20)); // 20 minutes duration
        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(16, 0), LocalTime.of(16, 30)); // 30 minutes duration

        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4, existingSlot5);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working TimeSlot 3 : (8:00 -> 12:00)
        LocalTime workingStart3 = LocalTime.of(8, 0);
        LocalTime workingEnd3 = LocalTime.of(12, 0);

        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(8, 0), LocalTime.of(8, 15)); // 15 minutes duration
        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(9, 20)); // 20 minutes duration
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 30)); // 30 minutes duration

        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot6, existingSlot7, existingSlot8);
        WorkingTimeInterval workingTimeSlot3 = new WorkingTimeInterval(workingStart3, workingEnd3, existingBooked3);


        // Working TimeSlot 4 : (14:30 -> 17:30)
        LocalTime workingStart4 = LocalTime.of(14, 30);
        LocalTime workingEnd4 = LocalTime.of(17, 30);

        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(14, 30), LocalTime.of(14, 45)); // 15 minutes duration
        TimeSlot existingSlot10 = new TimeSlot(LocalTime.of(15, 0), LocalTime.of(15, 25)); // 25 minutes duration
        TimeSlot existingSlot11 = new TimeSlot(LocalTime.of(16, 0), LocalTime.of(16, 30)); // 30 minutes duration

        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot9, existingSlot10, existingSlot11);
        WorkingTimeInterval workingTimeSlot4 = new WorkingTimeInterval(workingStart4, workingEnd4, existingBooked4);

        // Working TimeSlot 5 : (9:00 -> 12:30)
        LocalTime workingStart5 = LocalTime.of(9, 0);
        LocalTime workingEnd5 = LocalTime.of(12, 30);
        TimeSlot existingSlot12 = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(9, 15));  // 15 minutes
        TimeSlot existingSlot13 = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 20)); // 20 minutes
        TimeSlot existingSlot14 = new TimeSlot(LocalTime.of(11, 0), LocalTime.of(11, 30)); // 30 minutes
        List<TimeSlot> existingBooked5 = Arrays.asList(existingSlot12, existingSlot13, existingSlot14);
        WorkingTimeInterval workingTimeSlot5 = new WorkingTimeInterval(workingStart5, workingEnd5, existingBooked5);

        // Working TimeSlot 6 : (14:30 to 18:30)
        LocalTime workingStart6 = LocalTime.of(14, 30);
        LocalTime workingEnd6 = LocalTime.of(18, 30);
        TimeSlot existingSlot15 = new TimeSlot(LocalTime.of(14, 30), LocalTime.of(14, 45)); // 15 minutes
        TimeSlot existingSlot16 = new TimeSlot(LocalTime.of(15, 30), LocalTime.of(15, 50)); // 20 minutes
        TimeSlot existingSlot17 = new TimeSlot(LocalTime.of(16, 30), LocalTime.of(17, 0));  // 30 minutes
        List<TimeSlot> existingBooked6 = Arrays.asList(existingSlot15, existingSlot16, existingSlot17);
        WorkingTimeInterval workingTimeSlot6 = new WorkingTimeInterval(workingStart6, workingEnd6, existingBooked6);


        // First Working Day with WorkingTimeSlot1 and WorkingTimeSlot2
        LocalDate dateForFirstWorkingDay = LocalDate.of(2023, 12, 17); // Example date
        WorkingDay workingDay1 = new WorkingDay(dateForFirstWorkingDay, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Second Working Day with WorkingTimeSlot3 and WorkingTimeSlot4
        LocalDate dateForSecondWorkingDay = LocalDate.of(2023, 12, 18); // Another example date
        WorkingDay workingDay2 = new WorkingDay(dateForSecondWorkingDay, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Third Working Day with workingTimeSlot5 and workingTimeSlot6
        LocalDate dateForThirdWorkingDay = LocalDate.of(2023, 12, 19); // Example date
        WorkingDay workingDay3 = new WorkingDay(dateForThirdWorkingDay, Arrays.asList(workingTimeSlot5, workingTimeSlot6));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 17);
        LocalDate workingEnd = LocalDate.of(2023, 12, 20);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2, workingDay3));


        // Given TimeSlot
        LocalTime slotStart = LocalTime.of(10, 10);
        LocalTime slotEnd = LocalTime.of(10, 30);
        TimeSlot givenTimeSlot = new TimeSlot(slotStart, slotEnd);

        // Given Date
        LocalDate bookingDate = LocalDate.of(2023, 12, 19);

        // When, Then
        assertThrows(BookingException.class, () -> workingPeriod.book(bookingDate, givenTimeSlot));

    }


    // TestGetAvailableSlotsSize
    @Test
    public void testGetAvailableSlotsSize() {

        // Given

        // Working TimeSlot 1 : (8:00 -> 12:30)
        LocalTime workingStart1 = LocalTime.of(8, 0);
        LocalTime workingEnd1 = LocalTime.of(12, 30);

        TimeSlot existingSlot1 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot2 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked1 = Arrays.asList(existingSlot1, existingSlot2);
        WorkingTimeInterval workingTimeSlot1 = new WorkingTimeInterval(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2 : (14:00 -> 18:00)
        LocalTime workingStart2 = LocalTime.of(14, 0);
        LocalTime workingEnd2 = LocalTime.of(18, 0);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(14, 0), LocalTime.of(14, 15)); // 15 minutes duration
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(15, 0), LocalTime.of(15, 20)); // 20 minutes duration
        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(16, 0), LocalTime.of(16, 30)); // 30 minutes duration

        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4, existingSlot5);
        WorkingTimeInterval workingTimeSlot2 = new WorkingTimeInterval(workingStart2, workingEnd2, existingBooked2);

        // Working TimeSlot 3 : (8:00 -> 12:00)
        LocalTime workingStart3 = LocalTime.of(8, 0);
        LocalTime workingEnd3 = LocalTime.of(12, 0);

        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(8, 0), LocalTime.of(8, 15)); // 15 minutes duration
        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(9, 20)); // 20 minutes duration
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(10, 0), LocalTime.of(10, 30)); // 30 minutes duration

        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot6, existingSlot7, existingSlot8);
        WorkingTimeInterval workingTimeSlot3 = new WorkingTimeInterval(workingStart3, workingEnd3, existingBooked3);


        // Working TimeSlot 4 : (14:30 -> 17:30)
        LocalTime workingStart4 = LocalTime.of(14, 30);
        LocalTime workingEnd4 = LocalTime.of(17, 30);

        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(14, 30), LocalTime.of(14, 45)); // 15 minutes duration
        TimeSlot existingSlot10 = new TimeSlot(LocalTime.of(15, 0), LocalTime.of(15, 25)); // 25 minutes duration
        TimeSlot existingSlot11 = new TimeSlot(LocalTime.of(16, 0), LocalTime.of(16, 30)); // 30 minutes duration

        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot9, existingSlot10, existingSlot11);
        WorkingTimeInterval workingTimeSlot4 = new WorkingTimeInterval(workingStart4, workingEnd4, existingBooked4);


        // First Working Day with WorkingTimeSlot1 and WorkingTimeSlot2
        LocalDate dateForFirstWorkingDay = LocalDate.of(2023, 12, 23); // Example date
        WorkingDay workingDay1 = new WorkingDay(dateForFirstWorkingDay, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Second Working Day with WorkingTimeSlot3 and WorkingTimeSlot4
        LocalDate dateForSecondWorkingDay = LocalDate.of(2023, 12, 24); // Another example date
        WorkingDay workingDay2 = new WorkingDay(dateForSecondWorkingDay, Arrays.asList(workingTimeSlot3, workingTimeSlot4));


        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 23);
        LocalDate workingEnd = LocalDate.of(2023, 12, 26);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2));


        // When
        int actualAvailableSlotsSize = workingPeriod.getAvailableSlotsSize(Duration.ofMinutes(30));

        // Then
        assertEquals(20, actualAvailableSlotsSize);

    }


}