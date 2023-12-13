package com.mydoctor.domaine.appointment;

import org.junit.jupiter.api.Test;

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
        WorkingTimeSlot workingTimeSlot1 = new WorkingTimeSlot(workingStart1, workingEnd1, existingBooked1);

        // Working TimeSlot 2
        LocalTime workingStart2 = LocalTime.of(8, 0);
        LocalTime workingEnd2 = LocalTime.of(12, 30);

        TimeSlot existingSlot3 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot4 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked2 = Arrays.asList(existingSlot3, existingSlot4);
        WorkingTimeSlot workingTimeSlot2 = new WorkingTimeSlot(workingStart2, workingEnd2, existingBooked2);

        // Working TimeSlot 3
        LocalTime workingStart3 = LocalTime.of(8, 0);
        LocalTime workingEnd3 = LocalTime.of(12, 30);

        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot5, existingSlot6);
        WorkingTimeSlot workingTimeSlot3 = new WorkingTimeSlot(workingStart3, workingEnd3, existingBooked3);

        // Working TimeSlot 4
        LocalTime workingStart4 = LocalTime.of(8, 0);
        LocalTime workingEnd4 = LocalTime.of(12, 30);

        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(9,30), LocalTime.of(10,30));
        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot7, existingSlot8, existingSlot9);
        WorkingTimeSlot workingTimeSlot4 = new WorkingTimeSlot(workingStart4, workingEnd4, existingBooked4);

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


}