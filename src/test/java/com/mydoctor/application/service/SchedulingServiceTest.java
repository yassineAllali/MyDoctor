package com.mydoctor.application.service;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.adapter.PatientRepositoryAdapter;
import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.application.resource.PatientResource;
import com.mydoctor.application.resource.WorkingIntervalResource;
import com.mydoctor.domaine.appointment.booking.BookingException;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SchedulingServiceTest {

    private SchedulingService schedulingService;

    @Mock
    private AppointmentRepositoryAdapter appointmentRepository;
    @Mock
    private WorkingIntervalRepositoryAdapter workingIntervalRepository;
    @Mock
    private PatientRepositoryAdapter patientRepository;
    @Mock
    private MedicalOfficeRepositoryAdapter medicalOfficeRepository;

    private AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        schedulingService = new SchedulingService(appointmentRepository, workingIntervalRepository, patientRepository, medicalOfficeRepository);
    }

    @Test
    void testScheduleAppointment() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 23);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(10, 0);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);

        CreatePatientCommand givenPatient = new CreatePatientCommand("Yassine");

        Long givenMedOfficeId = 123456l;

        // When
        when(workingIntervalRepository.get(123456l, LocalDate.of(2024, 1, 23)))
                .thenReturn(Arrays.asList(new WorkingIntervalResource(LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), null)));
        when(medicalOfficeRepository.get(123456l)).thenReturn(Optional.of(new MedicalOfficeResource(123456l, "med_1")));

        when(patientRepository.save(any(PatientResource.class))).then(args -> {
            PatientResource resource = args.getArgument(0, PatientResource.class);
            return new PatientResource(123l, resource.name());
        });

        when(appointmentRepository.save(any(AppointmentResource.class))).then(args -> {
           AppointmentResource resource = args.getArgument(0, AppointmentResource.class);
           return new AppointmentResource(123l, resource.patient(), resource.medicalOffice(), resource.date(), resource.start(), resource.end());
        });

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

    @Test
    void testScheduleAppointmentShouldFailIfNotInSameDate() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 26);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(10, 0);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);

        CreatePatientCommand givenPatient = new CreatePatientCommand("Yassine");

        Long givenMedOfficeId = 123456l;

        // When
        when(workingIntervalRepository.get(123456l, LocalDate.of(2024, 1, 23)))
                .thenReturn(Arrays.asList(new WorkingIntervalResource(LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), null)));
        when(medicalOfficeRepository.get(123456l)).thenReturn(Optional.of(new MedicalOfficeResource(123456l, "med_1")));

        when(patientRepository.save(any(PatientResource.class))).then(args -> {
            PatientResource resource = args.getArgument(0, PatientResource.class);
            return new PatientResource(123l, resource.name());
        });

        when(appointmentRepository.save(any(AppointmentResource.class))).then(args -> {
            AppointmentResource resource = args.getArgument(0, AppointmentResource.class);
            return new AppointmentResource(123l, resource.patient(), resource.medicalOffice(), resource.date(), resource.start(), resource.end());
        });


        // When, Then
        assertThrows(BookingException.class, () -> schedulingService.schedule(givenAppointment, givenPatient, givenMedOfficeId));
    }

    @Test
    void testScheduleAppointmentShouldFailIfNotInsideWorkingInterval() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 23);
        LocalTime start = LocalTime.of(12, 0);
        LocalTime end = LocalTime.of(13, 0);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);

        CreatePatientCommand givenPatient = new CreatePatientCommand("Yassine");

        Long givenMedOfficeId = 123456l;

        // When
        when(workingIntervalRepository.get(123456l, LocalDate.of(2024, 1, 23)))
                .thenReturn(Arrays.asList(new WorkingIntervalResource(LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), null)));
        when(medicalOfficeRepository.get(123456l)).thenReturn(Optional.of(new MedicalOfficeResource(123456l, "med_1")));

        when(patientRepository.save(any(PatientResource.class))).then(args -> {
            PatientResource resource = args.getArgument(0, PatientResource.class);
            return new PatientResource(123l, resource.name());
        });

        when(appointmentRepository.save(any(AppointmentResource.class))).then(args -> {
            AppointmentResource resource = args.getArgument(0, AppointmentResource.class);
            return new AppointmentResource(123l, resource.patient(), resource.medicalOffice(), resource.date(), resource.start(), resource.end());
        });


        // When, Then
        assertThrows(BookingException.class, () -> schedulingService.schedule(givenAppointment, givenPatient, givenMedOfficeId));
    }

    @Test
    void testScheduleAppointmentShouldFailIfConflictWithAppointment() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 23);
        LocalTime start = LocalTime.of(9, 30);
        LocalTime end = LocalTime.of(10, 30);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);

        CreatePatientCommand givenPatient = new CreatePatientCommand("Yassine");

        Long givenMedOfficeId = 123456l;

        // When
        when(workingIntervalRepository.get(123456l, LocalDate.of(2024, 1, 23)))
                .thenReturn(Arrays.asList(new WorkingIntervalResource(LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), List.of(new AppointmentResource(1l, null, null, LocalDate.of(2024, 1, 23), LocalTime.of(9, 0), LocalTime.of(10, 0))))));
        when(medicalOfficeRepository.get(123456l)).thenReturn(Optional.of(new MedicalOfficeResource(123456l, "med_1")));

        when(patientRepository.save(any(PatientResource.class))).then(args -> {
            PatientResource resource = args.getArgument(0, PatientResource.class);
            return new PatientResource(123l, resource.name());
        });

        when(appointmentRepository.save(any(AppointmentResource.class))).then(args -> {
            AppointmentResource resource = args.getArgument(0, AppointmentResource.class);
            return new AppointmentResource(123l, resource.patient(), resource.medicalOffice(), resource.date(), resource.start(), resource.end());
        });


        // When, Then
        assertThrows(BookingException.class, () -> schedulingService.schedule(givenAppointment, givenPatient, givenMedOfficeId));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

}