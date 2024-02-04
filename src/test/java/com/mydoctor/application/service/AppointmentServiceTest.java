package com.mydoctor.application.service;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.application.resource.TimeSlotResource;
import com.mydoctor.infrastructure.entity.AppointmentEntity;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @Mock
    private AppointmentRepositoryAdapter appointmentRepository;
    @Mock
    private WorkingIntervalRepositoryAdapter workingIntervalRepository;
    private AppointmentService appointmentService;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        this.appointmentService = new AppointmentService(appointmentRepository, workingIntervalRepository);
    }

    @Test
    public void testGetAppointment() {
        // Given
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setId(123l);

        // When
        when(appointmentRepository.get(123l)).thenReturn(Optional.of(appointmentEntity));
        AppointmentResource actualAppointment = appointmentService.getAppointment(123l);

        // Then
        assertNotNull(actualAppointment);
        assertEquals(123l, actualAppointment.id());
    }

    @Test
    public void testGetAppointmentRaisesNotFoundException() {
        // Given
        long id = 123l;

        // when
        when(appointmentRepository.get(123l)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> appointmentService.getAppointment(id));
    }

    @Test
    public void testGetPatientAppointments() {
        // Given
        long patientId = 123l;

        // When
        appointmentService.getPatientAppointments(patientId);

        // Then
        verify(appointmentRepository, times(1)).getPatientAppointments(123l);
    }

    @Test
    public void testCancelAppointment() {
        // Given
        AppointmentEntity appointmentEntity = new AppointmentEntity(123l, null, null, null, LocalDate.of(2024, 2, 3), LocalTime.of(9, 10), LocalTime.of(9, 30), "BOOKED");

        // When
        when(appointmentRepository.get(123l)).thenReturn(Optional.of(appointmentEntity));
        AppointmentResource actualAppointment = appointmentService.cancelAppointment(123l);

        // Then
        assertNotNull(actualAppointment);
        assertEquals("CANCELED", actualAppointment.status());
    }

    @Test
    public void testGetAvailableSlotsForOneDay() {
        // Given
        LocalDate date = LocalDate.of(2024, 2, 4);
        long medOfficeId = 123l;
        List<AppointmentEntity> morningAppointments = List.of(
                new AppointmentEntity(1L, null, null, null, date, LocalTime.of(8, 0), LocalTime.of(8, 20), "BOOKED"),
                new AppointmentEntity(2L, null, null, null, date, LocalTime.of(9, 0), LocalTime.of(9, 20), "BOOKED"),
                new AppointmentEntity(3L, null, null, null, date, LocalTime.of(10, 0), LocalTime.of(10, 20), "BOOKED")
        );
        WorkingIntervalEntity morningWorkingInterval = new WorkingIntervalEntity(1L, null, date, LocalTime.of(8, 0), LocalTime.of(12, 0), morningAppointments);

        List<AppointmentEntity> afternoonAppointments = List.of(
                new AppointmentEntity(4L, null, null, null, date, LocalTime.of(14, 0), LocalTime.of(14, 20), "BOOKED"),
                new AppointmentEntity(5L, null, null, null, date, LocalTime.of(15, 0), LocalTime.of(15, 20), "BOOKED"),
                new AppointmentEntity(6L, null, null, null, date, LocalTime.of(16, 0), LocalTime.of(16, 20), "BOOKED")
        );
        WorkingIntervalEntity afternoonWorkingInterval = new WorkingIntervalEntity(2L, null, date, LocalTime.of(14, 0), LocalTime.of(18, 0), afternoonAppointments);

        // When
        when(workingIntervalRepository.get(medOfficeId, date)).thenReturn(List.of(morningWorkingInterval, afternoonWorkingInterval));
        List<TimeSlotResource> timeSlots = appointmentService.getAvailableSlots(medOfficeId, date, Duration.ofMinutes(20));

        // Then
        assertEquals(18, timeSlots.size());
        assertEquals(LocalTime.of(8, 20), timeSlots.get(0).start());
        assertEquals(LocalTime.of(18, 0), timeSlots.get(17).end());
    }

    @Test
    public void testGetAvailableSlotsForPeriod() {
        // Given
        long medOfficeId = 123l;

        // First working day
        LocalDate date1 = LocalDate.of(2024, 2, 4);
        List<AppointmentEntity> morningAppointments = List.of(
                new AppointmentEntity(1L, null, null, null, date1, LocalTime.of(8, 0), LocalTime.of(8, 20), "BOOKED"),
                new AppointmentEntity(2L, null, null, null, date1, LocalTime.of(9, 0), LocalTime.of(9, 20), "BOOKED"),
                new AppointmentEntity(3L, null, null, null, date1, LocalTime.of(10, 0), LocalTime.of(10, 20), "BOOKED")
        );
        WorkingIntervalEntity morningWorkingInterval = new WorkingIntervalEntity(1L, null, date1, LocalTime.of(8, 0), LocalTime.of(12, 0), morningAppointments);

        List<AppointmentEntity> afternoonAppointments = List.of(
                new AppointmentEntity(4L, null, null, null, date1, LocalTime.of(14, 0), LocalTime.of(14, 20), "BOOKED"),
                new AppointmentEntity(5L, null, null, null, date1, LocalTime.of(15, 0), LocalTime.of(15, 20), "BOOKED"),
                new AppointmentEntity(6L, null, null, null, date1, LocalTime.of(16, 0), LocalTime.of(16, 20), "BOOKED")
        );
        WorkingIntervalEntity afternoonWorkingInterval = new WorkingIntervalEntity(2L, null, date1, LocalTime.of(14, 0), LocalTime.of(18, 0), afternoonAppointments);

        // Second working day
        LocalDate date2 = LocalDate.of(2024, 2, 5);
        List<AppointmentEntity> morningAppointments2 = List.of(
                new AppointmentEntity(7L, null, null, null, date2, LocalTime.of(8, 0), LocalTime.of(8, 20), "BOOKED"),
                new AppointmentEntity(8L, null, null, null, date2, LocalTime.of(9, 0), LocalTime.of(9, 20), "BOOKED"),
                new AppointmentEntity(9L, null, null, null, date2, LocalTime.of(10, 0), LocalTime.of(10, 20), "BOOKED")
        );
        WorkingIntervalEntity morningWorkingInterval2 = new WorkingIntervalEntity(3L, null, date2, LocalTime.of(8, 0), LocalTime.of(12, 0), morningAppointments2);

        List<AppointmentEntity> afternoonAppointments2 = List.of(
                new AppointmentEntity(10L, null, null, null, date2, LocalTime.of(14, 0), LocalTime.of(14, 20), "BOOKED"),
                new AppointmentEntity(11L, null, null, null, date2, LocalTime.of(15, 0), LocalTime.of(15, 20), "BOOKED"),
                new AppointmentEntity(12L, null, null, null, date2, LocalTime.of(16, 0), LocalTime.of(16, 20), "BOOKED")
        );
        WorkingIntervalEntity afternoonWorkingInterval2 = new WorkingIntervalEntity(4L, null, date2, LocalTime.of(14, 0), LocalTime.of(18, 0), afternoonAppointments2);

        // Third working day
        LocalDate date3 = LocalDate.of(2024, 2, 6);
        List<AppointmentEntity> morningAppointments3 = List.of(
                new AppointmentEntity(13L, null, null, null, date3, LocalTime.of(8, 0), LocalTime.of(8, 20), "BOOKED"),
                new AppointmentEntity(14L, null, null, null, date3, LocalTime.of(9, 0), LocalTime.of(9, 20), "BOOKED"),
                new AppointmentEntity(15L, null, null, null, date3, LocalTime.of(10, 0), LocalTime.of(10, 20), "BOOKED")
        );
        WorkingIntervalEntity morningWorkingInterval3 = new WorkingIntervalEntity(5L, null, date3, LocalTime.of(8, 0), LocalTime.of(12, 0), morningAppointments3);

        List<AppointmentEntity> afternoonAppointments3 = List.of(
                new AppointmentEntity(16L, null, null, null, date3, LocalTime.of(14, 0), LocalTime.of(14, 20), "BOOKED"),
                new AppointmentEntity(17L, null, null, null, date3, LocalTime.of(15, 0), LocalTime.of(15, 20), "BOOKED"),
                new AppointmentEntity(18L, null, null, null, date3, LocalTime.of(16, 0), LocalTime.of(16, 20), "BOOKED")
        );
        WorkingIntervalEntity afternoonWorkingInterval3 = new WorkingIntervalEntity(6L, null, date3, LocalTime.of(14, 0), LocalTime.of(18, 0), afternoonAppointments3);

        // Create a list of all working time intervals
        List<WorkingIntervalEntity> allWorkingIntervals = List.of(
                morningWorkingInterval, afternoonWorkingInterval,
                morningWorkingInterval2, afternoonWorkingInterval2,
                morningWorkingInterval3, afternoonWorkingInterval3
        );

        // When
        when(workingIntervalRepository.get(medOfficeId, date1, date3)).thenReturn(allWorkingIntervals);
        List<TimeSlotResource> timeSlots = appointmentService.getAvailableSlots(medOfficeId, date1, date3, Duration.ofMinutes(20));

        // Then
        assertEquals(54, timeSlots.size());
        assertEquals(LocalTime.of(8, 20), timeSlots.get(0).start());
        assertEquals(LocalTime.of(18, 0), timeSlots.get(53).end());
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}