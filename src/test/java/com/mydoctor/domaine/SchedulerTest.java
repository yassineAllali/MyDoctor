package com.mydoctor.domaine;

import com.mydoctor.domaine.appointment.Appointment;
import com.mydoctor.domaine.appointment.Scheduler;
import com.mydoctor.domaine.appointment.booking.*;
import com.mydoctor.domaine.appointment.booking.exception.BookingException;
import com.mydoctor.domaine.medical.MedicalOffice;
import com.mydoctor.domaine.medical.Patient;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {

    // getAppointmentsSize

    @Test
    public void testGetAppointmentsSize() {
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
        LocalTime workingStart3 = LocalTime.of(14, 0);
        LocalTime workingEnd3 = LocalTime.of(17, 30);

        TimeSlot existingSlot5 = new TimeSlot(LocalTime.of(15,0), LocalTime.of(15,30));
        TimeSlot existingSlot6 = new TimeSlot(LocalTime.of(16,0), LocalTime.of(16,30));
        List<TimeSlot> existingBooked3 = Arrays.asList(existingSlot5, existingSlot6);
        WorkingTimeInterval workingTimeSlot3 = new WorkingTimeInterval(workingStart3, workingEnd3, existingBooked3);

        // Working TimeSlot 4
        LocalTime workingStart4 = LocalTime.of(8, 0);
        LocalTime workingEnd4 = LocalTime.of(12, 30);

        TimeSlot existingSlot7 = new TimeSlot(LocalTime.of(8,0), LocalTime.of(8,30));
        TimeSlot existingSlot8 = new TimeSlot(LocalTime.of(9,0), LocalTime.of(9,30));
        TimeSlot existingSlot9 = new TimeSlot(LocalTime.of(9,30), LocalTime.of(10,30));
        TimeSlot existingSlot10 = new TimeSlot(LocalTime.of(10,30), LocalTime.of(11,0));
        TimeSlot existingSlot11 = new TimeSlot(LocalTime.of(11,0), LocalTime.of(12,0));
        List<TimeSlot> existingBooked4 = Arrays.asList(existingSlot7, existingSlot8, existingSlot9, existingSlot10, existingSlot11);
        WorkingTimeInterval workingTimeSlot4 = new WorkingTimeInterval(workingStart4, workingEnd4, existingBooked4);

        // Working Day 1
        LocalDate date1 = LocalDate.of(2023, 12,21);
        WorkingDay workingDay1 = new WorkingDay(date1, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Working Day 2
        LocalDate date2 = LocalDate.of(2023, 12,22);
        WorkingDay workingDay2 = new WorkingDay(date2, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 21);
        LocalDate workingEnd = LocalDate.of(2023, 12, 23);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2));

        // Scheduler
        Scheduler scheduler = new Scheduler(workingPeriod);

        // When
        int actualAppointmentsSize = scheduler.getAppointmentsSize();

        // Then
        assertEquals(11, actualAppointmentsSize);
    }

    // Schedule

    // Succeed
    @Test
    public void testScheduleShouldSucceed() {

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
        LocalDate dateForFirstWorkingDay = LocalDate.of(2023, 12, 21);
        WorkingDay workingDay1 = new WorkingDay(dateForFirstWorkingDay, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Second Working Day with WorkingTimeSlot3 and WorkingTimeSlot4
        LocalDate dateForSecondWorkingDay = LocalDate.of(2023, 12, 22);
        WorkingDay workingDay2 = new WorkingDay(dateForSecondWorkingDay, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Third Working Day with workingTimeSlot5 and workingTimeSlot6
        LocalDate dateForThirdWorkingDay = LocalDate.of(2023, 12, 23);
        WorkingDay workingDay3 = new WorkingDay(dateForThirdWorkingDay, Arrays.asList(workingTimeSlot5, workingTimeSlot6));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 21);
        LocalDate workingEnd = LocalDate.of(2023, 12, 22);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2, workingDay3));

        // Scheduler
        Scheduler scheduler = new Scheduler(workingPeriod);

        // Given Appointment
        LocalTime appointmentStart = LocalTime.of(16, 0);
        LocalTime appointmentEnd = LocalTime.of(16, 30);
        TimeSlot givenTimeSlot = new TimeSlot(appointmentStart, appointmentEnd);
        LocalDate appointmentDate = LocalDate.of(2023, 12, 23);

        Appointment givenAppointment = new Appointment(appointmentDate, givenTimeSlot, new Patient("Yassine"));

        // When
        scheduler.schedule(givenAppointment);
        int actualAppointmentsSize = scheduler.getAppointmentsSize();

        // Then
        assertEquals(18, actualAppointmentsSize);

    }

    // Fail
    @Test
    public void testScheduleShouldFailIfNotInsidePeriode() {

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
        LocalDate dateForFirstWorkingDay = LocalDate.of(2023, 12, 21);
        WorkingDay workingDay1 = new WorkingDay(dateForFirstWorkingDay, Arrays.asList(workingTimeSlot1, workingTimeSlot2));

        // Second Working Day with WorkingTimeSlot3 and WorkingTimeSlot4
        LocalDate dateForSecondWorkingDay = LocalDate.of(2023, 12, 22);
        WorkingDay workingDay2 = new WorkingDay(dateForSecondWorkingDay, Arrays.asList(workingTimeSlot3, workingTimeSlot4));

        // Third Working Day with workingTimeSlot5 and workingTimeSlot6
        LocalDate dateForThirdWorkingDay = LocalDate.of(2023, 12, 23);
        WorkingDay workingDay3 = new WorkingDay(dateForThirdWorkingDay, Arrays.asList(workingTimeSlot5, workingTimeSlot6));

        // Working Period
        LocalDate workingStart = LocalDate.of(2023, 12, 21);
        LocalDate workingEnd = LocalDate.of(2023, 12, 22);
        BookablePeriod workingPeriod = new WorkingPeriod(workingStart, workingEnd, Arrays.asList(workingDay1, workingDay2, workingDay3));

        // Scheduler
        Scheduler scheduler = new Scheduler(workingPeriod);

        // Given Appointment
        LocalTime appointmentStart = LocalTime.of(16, 0);
        LocalTime appointmentEnd = LocalTime.of(16, 30);
        TimeSlot givenTimeSlot = new TimeSlot(appointmentStart, appointmentEnd);
        LocalDate appointmentDate = LocalDate.of(2023, 12, 26);

        Appointment givenAppointment = new Appointment(appointmentDate, givenTimeSlot, new Patient("Yassine"));

        // When, Then
        assertThrows(BookingException.class, () -> scheduler.schedule(givenAppointment));

    }


}