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
import com.mydoctor.infrastructure.entity.AppointmentEntity;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.entity.PatientEntity;
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

        MedicalOfficeEntity givenMedicalOffice = new MedicalOfficeEntity(1l, "med_1");

        // When
        when(workingIntervalRepository.get(1l, LocalDate.of(2024, 1, 23), LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .thenReturn(Arrays.asList(new WorkingIntervalEntity(123l, givenMedicalOffice, LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), null)));

        when(patientRepository.save(any(PatientEntity.class))).then(args -> {
            PatientEntity entity = args.getArgument(0, PatientEntity.class);
            return new PatientEntity(123l, entity.getName());
        });

        when(appointmentRepository.save(any(AppointmentEntity.class))).then(args -> {
            AppointmentEntity entity = args.getArgument(0, AppointmentEntity.class);
           return new AppointmentEntity(123l, entity.getPatient(), entity.getMedicalOffice(), entity.getWorkingInterval(), entity.getDate(), entity.getStart(), entity.getEnd());
        });

        AppointmentResource actualAppointment = schedulingService.schedule(givenAppointment, givenPatient, 1l);

        // Then
        assertNotNull(actualAppointment);
        assertNotNull(actualAppointment.id());
        assertEquals(appointmentDate, actualAppointment.date());
        assertEquals(start, actualAppointment.start());
        assertEquals(end, actualAppointment.end());
        assertEquals("Yassine", actualAppointment.patient().name());
        assertEquals(1l, actualAppointment.medicalOffice().id());
        assertEquals("med_1", actualAppointment.medicalOffice().name());
    }

    @Test
    void testScheduleAppointmentWithExistingPatient() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 23);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(10, 0);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);
        PatientEntity givenPatient = new PatientEntity(123l, "Yassine");
        MedicalOfficeEntity givenMedicalOffice = new MedicalOfficeEntity(1l, "med_1");

        // When
        when(workingIntervalRepository.get(1l, LocalDate.of(2024, 1, 23), LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .thenReturn(Arrays.asList(new WorkingIntervalEntity(123l, givenMedicalOffice, LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), null)));

        when(patientRepository.get(123l)).thenReturn(Optional.of(givenPatient));

        when(appointmentRepository.save(any(AppointmentEntity.class))).then(args -> {
            AppointmentEntity entity = args.getArgument(0, AppointmentEntity.class);
            return new AppointmentEntity(123l, entity.getPatient(), entity.getMedicalOffice(), entity.getWorkingInterval(), entity.getDate(), entity.getStart(), entity.getEnd());
        });

        AppointmentResource actualAppointment = schedulingService.schedule(givenAppointment, 1l, 123l);

        // Then
        assertNotNull(actualAppointment);
        assertNotNull(actualAppointment.id());
        assertEquals(appointmentDate, actualAppointment.date());
        assertEquals(start, actualAppointment.start());
        assertEquals(end, actualAppointment.end());
        assertEquals("Yassine", actualAppointment.patient().name());
        assertEquals(1l, actualAppointment.medicalOffice().id());
        assertEquals("med_1", actualAppointment.medicalOffice().name());
    }

    @Test
    void testScheduleAppointmentShouldFailIfNotInSameDate() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 26);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(10, 0);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);

        CreatePatientCommand givenPatient = new CreatePatientCommand("Yassine");

        MedicalOfficeEntity givenMedicalOffice = new MedicalOfficeEntity(1l, "med_1");

        // When
        when(workingIntervalRepository.get(1l, LocalDate.of(2024, 1, 23), LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .thenReturn(Arrays.asList(new WorkingIntervalEntity(123l, givenMedicalOffice, LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), null)));

        when(patientRepository.save(any(PatientEntity.class))).then(args -> {
            PatientEntity entity = args.getArgument(0, PatientEntity.class);
            return new PatientEntity(123l, entity.getName());
        });

        when(appointmentRepository.save(any(AppointmentEntity.class))).then(args -> {
            AppointmentEntity entity = args.getArgument(0, AppointmentEntity.class);
            return new AppointmentEntity(123l, entity.getPatient(), entity.getMedicalOffice(), entity.getWorkingInterval(), entity.getDate(), entity.getStart(), entity.getEnd());
        });

        // When, Then
        assertThrows(BookingException.class, () -> schedulingService.schedule(givenAppointment, givenPatient, 1l));
    }

    @Test
    void testScheduleAppointmentShouldFailIfNotInsideWorkingInterval() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 23);
        LocalTime start = LocalTime.of(12, 0);
        LocalTime end = LocalTime.of(13, 0);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);

        CreatePatientCommand givenPatient = new CreatePatientCommand("Yassine");

        MedicalOfficeEntity givenMedicalOffice = new MedicalOfficeEntity(1l, "med_1");

        // When
        when(workingIntervalRepository.get(1l, LocalDate.of(2024, 1, 23), LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .thenReturn(Arrays.asList(new WorkingIntervalEntity(123l, givenMedicalOffice, LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), null)));

        when(patientRepository.save(any(PatientEntity.class))).then(args -> {
            PatientEntity entity = args.getArgument(0, PatientEntity.class);
            return new PatientEntity(123l, entity.getName());
        });

        when(appointmentRepository.save(any(AppointmentEntity.class))).then(args -> {
            AppointmentEntity entity = args.getArgument(0, AppointmentEntity.class);
            return new AppointmentEntity(123l, entity.getPatient(), entity.getMedicalOffice(), entity.getWorkingInterval(), entity.getDate(), entity.getStart(), entity.getEnd());
        });


        // When, Then
        assertThrows(BookingException.class, () -> schedulingService.schedule(givenAppointment, givenPatient, 1l));
    }

    @Test
    void testScheduleAppointmentShouldFailIfConflictWithAppointment() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 23);
        LocalTime start = LocalTime.of(9, 30);
        LocalTime end = LocalTime.of(10, 30);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);

        CreatePatientCommand givenPatient = new CreatePatientCommand("Yassine");

        MedicalOfficeEntity givenMedicalOffice = new MedicalOfficeEntity(1l, "med_1");

        // When
        when(workingIntervalRepository.get(1l, LocalDate.of(2024, 1, 23), LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .thenReturn(Arrays.asList(new WorkingIntervalEntity(123l, givenMedicalOffice, LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), null)));

        when(patientRepository.save(any(PatientEntity.class))).then(args -> {
            PatientEntity entity = args.getArgument(0, PatientEntity.class);
            return new PatientEntity(123l, entity.getName());
        });

        when(appointmentRepository.save(any(AppointmentEntity.class))).then(args -> {
            AppointmentEntity entity = args.getArgument(0, AppointmentEntity.class);
            return new AppointmentEntity(123l, entity.getPatient(), entity.getMedicalOffice(), entity.getWorkingInterval(), entity.getDate(), entity.getStart(), entity.getEnd());
        });

        // When, Then
        assertThrows(BookingException.class, () -> schedulingService.schedule(givenAppointment, givenPatient, 1l));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

}