package com.mydoctor.application.service;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.infrastructure.entity.AppointmentEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @Mock
    private AppointmentRepositoryAdapter appointmentRepository;
    private AppointmentService appointmentService;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        this.appointmentService = new AppointmentService(appointmentRepository);
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

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}