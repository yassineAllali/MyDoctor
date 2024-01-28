package com.mydoctor.application.service;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    @Mock
    private AppointmentRepositoryAdapter repositoryAdapter;
    private AppointmentService appointmentService;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        this.appointmentService = new AppointmentService(repositoryAdapter);
    }

    @Test
    public void testGetPatientAppointments() {
        // Given
        long patientId = 123l;

        // When
        appointmentService.getPatientAppointments(patientId);

        // Then
        Mockito.verify(repositoryAdapter, Mockito.times(1)).getPatientAppointments(123l);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
}