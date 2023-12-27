package com.mydoctor.application.service;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.adapter.PatientRepositoryAdapter;
import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.resource.AppointmentResource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SchedulingServiceTest {

    private SchedulingService schedulingService;

    @Mock
    private AppointmentRepositoryAdapter appointmentRepository;
    @Mock
    private PatientRepositoryAdapter patientRepository;
    @Mock
    private MedicalOfficeRepositoryAdapter medicalOfficeRepository;

    private AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        schedulingService = new SchedulingService(appointmentRepository, patientRepository, medicalOfficeRepository);
    }

    @Test
    void testScheduleAppointment() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2023, 12, 27);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(10, 0);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);

        CreatePatientCommand givenPatient = new CreatePatientCommand("Yassine");

        Long givenMedOfficeId = 123456l;

        // When
        AppointmentResource actualAppointment = schedulingService.schedule(givenAppointment, givenPatient, givenMedOfficeId);

        // Then
        assertNotNull(actualAppointment);
        assertNotNull(actualAppointment.id());
        assertEquals(appointmentDate, actualAppointment.date());
        assertEquals(start, actualAppointment.start());
        assertEquals(end, actualAppointment.end());
        assertEquals(givenPatient.name(), actualAppointment.patient().name());
        assertEquals(givenMedOfficeId, actualAppointment.medicalOffice().id());
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

}