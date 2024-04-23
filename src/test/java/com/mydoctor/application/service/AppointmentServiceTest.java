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

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testGet() {
        // Given
        AppointmentEntity appointmentEntity = AppointmentEntity.builder().id(123456l).build();
        Long id = 123l;

        // When
        when(appointmentRepository.get(id)).thenReturn(Optional.of(appointmentEntity));
        AppointmentResource appointmentResource = appointmentService.get(id);

        // Then
        assertEquals(123456l, appointmentResource.id());
    }

    @Test
    void testGetThrowsNotFoundException() {
        // Given
        Long id = 123l;

        // When
        when(appointmentRepository.get(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> appointmentService.get(id));
    }

    @Test
    void testGetAll() {
        // Given
        List<AppointmentEntity> entities = List.of(
                AppointmentEntity.builder().id(1l).build(),
                AppointmentEntity.builder().id(2l).build(),
                AppointmentEntity.builder().id(3l).build()
        );

        // When
        when(appointmentRepository.getAll()).thenReturn(entities);
        List<AppointmentResource> appointments = appointmentService.getAll();

        // Then
        assertEquals(3, appointments.size());
        assertEquals(3l, appointments.get(2).id());
    }

    @Test
    void testCreate() {
        // Given
        AppointmentResource resource = AppointmentResource.builder().date(LocalDate.of(2024, 4, 7)).build();

        // When
        when(appointmentRepository.save(any())).then(args -> {
            AppointmentEntity entity = args.getArgument(0, AppointmentEntity.class);
            entity.setId(123l);
            return entity;
        });
        AppointmentResource appointment = appointmentService.create(resource);
        // Then
        assertEquals(LocalDate.of(2024, 4, 7), appointment.date());
        assertNotNull(appointment.id());
    }

    @Test
    void testCreateThrowsIllegalArgumentException() {
        // Given
        AppointmentResource resource = AppointmentResource.builder().id(123l).date(LocalDate.of(2024, 4, 7)).build();

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> appointmentService.create(resource));
    }

    @Test
    void testUpdate() {
        // Given
        AppointmentResource resource = AppointmentResource.builder().id(123456l).date(LocalDate.of(2024, 4, 7)).build();

        // When
        when(appointmentRepository.existById(123456l)).thenReturn(true);
        when(appointmentRepository.save(any())).then(args -> args.getArgument(0, AppointmentEntity.class));
        AppointmentResource appointment = appointmentService.update(resource);
        // Then
        assertEquals(LocalDate.of(2024, 4, 7), appointment.date());
        assertEquals(123456l, appointment.id());
    }

    @Test
    void testUpdateThrowsNotFoundException() {
        // Given
        AppointmentResource resource = AppointmentResource.builder().id(123456l).date(LocalDate.of(2024, 4, 7)).build();

        // When
        when(appointmentRepository.existById(123456l)).thenReturn(false);
        when(appointmentRepository.save(any())).then(args -> args.getArgument(0, AppointmentEntity.class));

        // Then
        assertThrows(NotFoundException.class, () -> appointmentService.update(resource));
    }

    @Test
    void testDelete() {
        // Given
        Long id = 123789l;

        // When
        when(appointmentRepository.existById(id)).thenReturn(true);
        appointmentService.delete(id);

        // Then
        verify(appointmentRepository, times(1)).delete(id);
    }

    @Test
    void testDeleteThrowsNotFoundException() {
        // Given
        Long id = 123789l;

        // When
        when(appointmentRepository.existById(id)).thenReturn(false);

        // Then
        assertThrows(NotFoundException.class, () -> appointmentService.delete(id));
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
        AppointmentEntity appointmentEntity = new AppointmentEntity(123l, null, null, null, null, LocalDate.of(2024, 2, 3), LocalTime.of(9, 10), LocalTime.of(9, 30), "BOOKED");

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
        long doctorId = 123l;
        List<AppointmentEntity> morningAppointments = List.of(
                new AppointmentEntity(1L, null, null, null, null, date, LocalTime.of(8, 0), LocalTime.of(8, 20), "BOOKED"),
                new AppointmentEntity(2L, null, null, null, null, date, LocalTime.of(9, 0), LocalTime.of(9, 20), "BOOKED"),
                new AppointmentEntity(3L, null, null, null, null, date, LocalTime.of(10, 0), LocalTime.of(10, 20), "BOOKED")
        );
        WorkingIntervalEntity morningWorkingInterval = new WorkingIntervalEntity(1L, date, LocalTime.of(8, 0), LocalTime.of(12, 0), null, null, morningAppointments);

        List<AppointmentEntity> afternoonAppointments = List.of(
                new AppointmentEntity(4L, null, null, null, null, date, LocalTime.of(14, 0), LocalTime.of(14, 20), "BOOKED"),
                new AppointmentEntity(5L, null, null, null, null, date, LocalTime.of(15, 0), LocalTime.of(15, 20), "BOOKED"),
                new AppointmentEntity(6L, null, null, null, null, date, LocalTime.of(16, 0), LocalTime.of(16, 20), "BOOKED")
        );
        WorkingIntervalEntity afternoonWorkingInterval = new WorkingIntervalEntity(2L, date, LocalTime.of(14, 0), LocalTime.of(18, 0), null, null, afternoonAppointments);

        // When
        when(workingIntervalRepository.get(medOfficeId, doctorId, date)).thenReturn(List.of(morningWorkingInterval, afternoonWorkingInterval));
        List<TimeSlotResource> timeSlots = appointmentService.getAvailableSlots(medOfficeId, doctorId, date, Duration.ofMinutes(20));

        // Then
        assertEquals(18, timeSlots.size());
        assertEquals(LocalTime.of(8, 20), timeSlots.get(0).start());
        assertEquals(LocalTime.of(18, 0), timeSlots.get(17).end());
    }

    @Test
    public void testGetAvailableSlotsForPeriod() {
        // Given
        long medOfficeId = 123l;
        long doctorId = 123l;

        // First working day
        LocalDate date1 = LocalDate.of(2024, 2, 4);
        List<AppointmentEntity> morningAppointments = List.of(
                new AppointmentEntity(1L, null, null, null, null, date1, LocalTime.of(8, 0), LocalTime.of(8, 20), "BOOKED"),
                new AppointmentEntity(2L, null, null, null, null, date1, LocalTime.of(9, 0), LocalTime.of(9, 20), "BOOKED"),
                new AppointmentEntity(3L, null, null, null, null, date1, LocalTime.of(10, 0), LocalTime.of(10, 20), "BOOKED")
        );
        WorkingIntervalEntity morningWorkingInterval = new WorkingIntervalEntity(1L, date1, LocalTime.of(8, 0), LocalTime.of(12, 0), null, null, morningAppointments);

        List<AppointmentEntity> afternoonAppointments = List.of(
                new AppointmentEntity(4L, null, null, null, null, date1, LocalTime.of(14, 0), LocalTime.of(14, 20), "BOOKED"),
                new AppointmentEntity(5L, null, null, null, null, date1, LocalTime.of(15, 0), LocalTime.of(15, 20), "BOOKED"),
                new AppointmentEntity(6L, null, null, null, null, date1, LocalTime.of(16, 0), LocalTime.of(16, 20), "BOOKED")
        );
        WorkingIntervalEntity afternoonWorkingInterval = new WorkingIntervalEntity(2L, date1, LocalTime.of(14, 0), LocalTime.of(18, 0), null, null, afternoonAppointments);

        // Second working day
        LocalDate date2 = LocalDate.of(2024, 2, 5);
        List<AppointmentEntity> morningAppointments2 = List.of(
                new AppointmentEntity(7L, null, null, null, null, date2, LocalTime.of(8, 0), LocalTime.of(8, 20), "BOOKED"),
                new AppointmentEntity(8L, null, null, null, null, date2, LocalTime.of(9, 0), LocalTime.of(9, 20), "BOOKED"),
                new AppointmentEntity(9L, null, null, null, null, date2, LocalTime.of(10, 0), LocalTime.of(10, 20), "BOOKED")
        );
        WorkingIntervalEntity morningWorkingInterval2 = new WorkingIntervalEntity(3L, date2, LocalTime.of(8, 0), LocalTime.of(12, 0), null, null, morningAppointments2);

        List<AppointmentEntity> afternoonAppointments2 = List.of(
                new AppointmentEntity(10L, null, null, null, null, date2, LocalTime.of(14, 0), LocalTime.of(14, 20), "BOOKED"),
                new AppointmentEntity(11L, null, null, null, null, date2, LocalTime.of(15, 0), LocalTime.of(15, 20), "BOOKED"),
                new AppointmentEntity(12L, null, null, null, null, date2, LocalTime.of(16, 0), LocalTime.of(16, 20), "BOOKED")
        );
        WorkingIntervalEntity afternoonWorkingInterval2 = new WorkingIntervalEntity(4L, date2, LocalTime.of(14, 0), LocalTime.of(18, 0), null, null, afternoonAppointments2);

        // Third working day
        LocalDate date3 = LocalDate.of(2024, 2, 6);
        List<AppointmentEntity> morningAppointments3 = List.of(
                new AppointmentEntity(13L, null, null, null, null, date3, LocalTime.of(8, 0), LocalTime.of(8, 20), "BOOKED"),
                new AppointmentEntity(14L, null, null, null, null, date3, LocalTime.of(9, 0), LocalTime.of(9, 20), "BOOKED"),
                new AppointmentEntity(15L, null, null, null, null, date3, LocalTime.of(10, 0), LocalTime.of(10, 20), "BOOKED")
        );
        WorkingIntervalEntity morningWorkingInterval3 = new WorkingIntervalEntity(5L, date3, LocalTime.of(8, 0), LocalTime.of(12, 0), null, null, morningAppointments3);

        List<AppointmentEntity> afternoonAppointments3 = List.of(
                new AppointmentEntity(16L, null, null, null, null, date3, LocalTime.of(14, 0), LocalTime.of(14, 20), "BOOKED"),
                new AppointmentEntity(17L, null, null, null, null, date3, LocalTime.of(15, 0), LocalTime.of(15, 20), "BOOKED"),
                new AppointmentEntity(18L, null, null, null, null, date3, LocalTime.of(16, 0), LocalTime.of(16, 20), "BOOKED")
        );
        WorkingIntervalEntity afternoonWorkingInterval3 = new WorkingIntervalEntity(6L, date3, LocalTime.of(14, 0), LocalTime.of(18, 0), null, null, afternoonAppointments3);

        // Create a list of all working time intervals
        List<WorkingIntervalEntity> allWorkingIntervals = List.of(
                morningWorkingInterval, afternoonWorkingInterval,
                morningWorkingInterval2, afternoonWorkingInterval2,
                morningWorkingInterval3, afternoonWorkingInterval3
        );

        // When
        when(workingIntervalRepository.get(medOfficeId, doctorId, date1, date3)).thenReturn(allWorkingIntervals);
        List<TimeSlotResource> timeSlots = appointmentService.getAvailableSlots(medOfficeId, doctorId, date1, date3, Duration.ofMinutes(20));

        // Then
        assertEquals(54, timeSlots.size());
        assertEquals(LocalTime.of(8, 20), timeSlots.get(0).start());
        assertEquals(LocalTime.of(18, 0), timeSlots.get(53).end());
    }

}