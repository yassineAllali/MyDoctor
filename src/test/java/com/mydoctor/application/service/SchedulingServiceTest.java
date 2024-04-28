package com.mydoctor.application.service;

import com.mydoctor.application.adapter.*;
import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.exception.BusinessException;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.domaine.appointment.booking.BookingException;
import com.mydoctor.infrastructure.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

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
    @Mock
    private DoctorRepositoryAdapter doctorRepository;

    private AutoCloseable openMocks;

    @BeforeEach
    void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        schedulingService = new SchedulingService(appointmentRepository, workingIntervalRepository, patientRepository, medicalOfficeRepository, doctorRepository);
    }

    @Test
    void testScheduleAppointment() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 23);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(10, 0);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);
        CreatePatientCommand givenPatient = new CreatePatientCommand("Yassine");

        MedicalOfficeEntity givenMedicalOffice = new MedicalOfficeEntity(1l, "med_1", null, null, null);
        DoctorEntity givenDoctor = new DoctorEntity(1l, "Doctor", null, null);

        // When
        when(medicalOfficeRepository.existById(1l)).thenReturn(true);
        when(doctorRepository.existById(1l)).thenReturn(true);
        when(workingIntervalRepository.get(1l, 1l, LocalDate.of(2024, 1, 23), LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .thenReturn(Arrays.asList(new WorkingIntervalEntity(123l, LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), givenDoctor, givenMedicalOffice, null)));

        when(patientRepository.save(any(PatientEntity.class))).then(args -> {
            PatientEntity entity = args.getArgument(0, PatientEntity.class);
            return new PatientEntity(123l, entity.getName());
        });

        when(appointmentRepository.save(any(AppointmentEntity.class))).then(args -> {
            AppointmentEntity entity = args.getArgument(0, AppointmentEntity.class);
           return new AppointmentEntity(123l, entity.getPatient(), entity.getMedicalOffice(), entity.getDoctor(), entity.getWorkingInterval(), entity.getDate(), entity.getStart(), entity.getEnd(), entity.getStatus());
        });

        AppointmentResource actualAppointment = schedulingService.schedule(givenAppointment, givenPatient, 1l, 1l);

        // Then
        assertNotNull(actualAppointment);
        assertNotNull(actualAppointment.id());
        assertEquals(appointmentDate, actualAppointment.date());
        assertEquals(start, actualAppointment.start());
        assertEquals(end, actualAppointment.end());
        assertEquals("BOOKED", actualAppointment.status());
        assertEquals("Yassine", actualAppointment.patient().name());
        assertEquals(1l, actualAppointment.medicalOffice().id());
        assertEquals("med_1", actualAppointment.medicalOffice().name());
        assertEquals("Doctor", actualAppointment.doctor().name());
    }

    @Test
    void testScheduleAppointmentWithExistingPatient() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 23);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(10, 0);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);
        PatientEntity givenPatient = new PatientEntity(123l, "Yassine");
        MedicalOfficeEntity givenMedicalOffice = new MedicalOfficeEntity(1l, "med_1", null, null, null);
        DoctorEntity givenDoctor = new DoctorEntity(1l, "Doctor", null, null);

        // When
        when(medicalOfficeRepository.existById(1l)).thenReturn(true);
        when(doctorRepository.existById(1l)).thenReturn(true);
        when(patientRepository.existById(123l)).thenReturn(true);
        when(workingIntervalRepository.get(1l, 1l, LocalDate.of(2024, 1, 23), LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .thenReturn(Arrays.asList(new WorkingIntervalEntity(123l, LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), givenDoctor, givenMedicalOffice, null)));

        when(patientRepository.get(123l)).thenReturn(Optional.of(givenPatient));

        when(appointmentRepository.save(any(AppointmentEntity.class))).then(args -> {
            AppointmentEntity entity = args.getArgument(0, AppointmentEntity.class);
            return new AppointmentEntity(123l, entity.getPatient(), entity.getMedicalOffice(), entity.getDoctor(), entity.getWorkingInterval(), entity.getDate(), entity.getStart(), entity.getEnd(), entity.getStatus());
        });

        AppointmentResource actualAppointment = schedulingService.schedule(givenAppointment, 1l, 1l, 123l);

        // Then
        assertNotNull(actualAppointment);
        assertNotNull(actualAppointment.id());
        assertEquals(appointmentDate, actualAppointment.date());
        assertEquals(start, actualAppointment.start());
        assertEquals(end, actualAppointment.end());
        assertEquals("BOOKED", actualAppointment.status());
        assertEquals("Yassine", actualAppointment.patient().name());
        assertEquals(1l, actualAppointment.medicalOffice().id());
        assertEquals("med_1", actualAppointment.medicalOffice().name());
        assertEquals("Doctor", actualAppointment.doctor().name());
    }

    @Test
    void testScheduleAppointmentShouldFailIfNotInSameDate() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 26);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(10, 0);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);

        CreatePatientCommand givenPatient = new CreatePatientCommand("Yassine");

        MedicalOfficeEntity givenMedicalOffice = new MedicalOfficeEntity(1l, "med_1", null, null, null);
        DoctorEntity givenDoctor = new DoctorEntity(1l, "Doctor", null, null);

        // When
        when(workingIntervalRepository.get(1l, 1l, LocalDate.of(2024, 1, 23), LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .thenReturn(Arrays.asList(new WorkingIntervalEntity(123l, LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), givenDoctor, givenMedicalOffice, null)));

        when(patientRepository.save(any(PatientEntity.class))).then(args -> {
            PatientEntity entity = args.getArgument(0, PatientEntity.class);
            return new PatientEntity(123l, entity.getName());
        });

        when(appointmentRepository.save(any(AppointmentEntity.class))).then(args -> {
            AppointmentEntity entity = args.getArgument(0, AppointmentEntity.class);
            return new AppointmentEntity(123l, entity.getPatient(), entity.getMedicalOffice(), entity.getDoctor(), entity.getWorkingInterval(), entity.getDate(), entity.getStart(), entity.getEnd(), entity.getStatus());
        });

        // When, Then
        assertThrows(BusinessException.class, () -> schedulingService.schedule(givenAppointment, givenPatient, 1l, 1l));
    }

    @Test
    void testScheduleAppointmentShouldFailIfNotInsideWorkingInterval() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 23);
        LocalTime start = LocalTime.of(12, 0);
        LocalTime end = LocalTime.of(13, 0);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);

        CreatePatientCommand givenPatient = new CreatePatientCommand("Yassine");

        MedicalOfficeEntity givenMedicalOffice = new MedicalOfficeEntity(1l, "med_1", null, null, null);
        DoctorEntity givenDoctor = new DoctorEntity(1l, "Doctor", null, null);

        // When
        when(workingIntervalRepository.get(1l, 1l, LocalDate.of(2024, 1, 23), LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .thenReturn(Arrays.asList(new WorkingIntervalEntity(123l, LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), givenDoctor, givenMedicalOffice, null)));

        when(patientRepository.save(any(PatientEntity.class))).then(args -> {
            PatientEntity entity = args.getArgument(0, PatientEntity.class);
            return new PatientEntity(123l, entity.getName());
        });

        when(appointmentRepository.save(any(AppointmentEntity.class))).then(args -> {
            AppointmentEntity entity = args.getArgument(0, AppointmentEntity.class);
            return new AppointmentEntity(123l, entity.getPatient(), entity.getMedicalOffice(), entity.getDoctor(), entity.getWorkingInterval(), entity.getDate(), entity.getStart(), entity.getEnd(), entity.getStatus());
        });


        // When, Then
        assertThrows(BusinessException.class, () -> schedulingService.schedule(givenAppointment, givenPatient, 1l, 1l));
    }

    @Test
    void testScheduleAppointmentShouldFailIfConflictWithAppointment() {
        // Given
        LocalDate appointmentDate = LocalDate.of(2024, 1, 23);
        LocalTime start = LocalTime.of(9, 30);
        LocalTime end = LocalTime.of(10, 30);
        CreateAppointmentCommand givenAppointment = new CreateAppointmentCommand(appointmentDate, start, end);

        CreatePatientCommand givenPatient = new CreatePatientCommand("Yassine");

        MedicalOfficeEntity givenMedicalOffice = new MedicalOfficeEntity(1l, "med_1", null, null, null);
        DoctorEntity givenDoctor = new DoctorEntity(1l, "Doctor", null, null);

        // When
        when(workingIntervalRepository.get(1l, 1l, LocalDate.of(2024, 1, 23), LocalTime.of(9, 0), LocalTime.of(10, 0)))
                .thenReturn(Arrays.asList(new WorkingIntervalEntity(123l, LocalDate.of(2024, 1, 23), LocalTime.of(8, 0), LocalTime.of(12, 0), givenDoctor, givenMedicalOffice, null)));

        when(patientRepository.save(any(PatientEntity.class))).then(args -> {
            PatientEntity entity = args.getArgument(0, PatientEntity.class);
            return new PatientEntity(123l, entity.getName());
        });

        when(appointmentRepository.save(any(AppointmentEntity.class))).then(args -> {
            AppointmentEntity entity = args.getArgument(0, AppointmentEntity.class);
            return new AppointmentEntity(123l, entity.getPatient(), entity.getMedicalOffice(), entity.getDoctor(), entity.getWorkingInterval(), entity.getDate(), entity.getStart(), entity.getEnd(), entity.getStatus());
        });

        // When, Then
        assertThrows(BusinessException.class, () -> schedulingService.schedule(givenAppointment, givenPatient, 1l, 1l));
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

}