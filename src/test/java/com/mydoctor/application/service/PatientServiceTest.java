package com.mydoctor.application.service;

import com.mydoctor.application.adapter.PatientRepositoryAdapter;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.resource.DoctorResource;
import com.mydoctor.application.resource.PatientResource;
import com.mydoctor.application.resource.SpecializationResource;
import com.mydoctor.infrastructure.entity.DoctorEntity;
import com.mydoctor.infrastructure.entity.PatientEntity;
import com.mydoctor.presentation.request.create.CreateDoctorRequest;
import com.mydoctor.presentation.request.create.CreatePatientRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepositoryAdapter patientRepository;

    private PatientService patientService;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        this.patientService = new PatientService(patientRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testGet() {
        // Given
        PatientEntity patientEntity = PatientEntity.builder().id(123456l).build();

        // When
        when(patientRepository.get(123456l)).thenReturn(Optional.of(patientEntity));
        PatientResource patientResource = patientService.get(123456l);

        // Then
        assertEquals(123456l, patientResource.id());
    }

    @Test
    void testGetThrowsNotFoundException() {
        // Given
        Long id = 123l;

        // When
        when(patientRepository.get(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> patientService.get(id));
    }

    @Test
    void testGetAll() {
        // Given
        List<PatientEntity> entities = List.of(
                PatientEntity.builder().id(1l).build(),
                PatientEntity.builder().id(2l).build(),
                PatientEntity.builder().id(3l).build()
        );

        // When
        when(patientRepository.getAll()).thenReturn(entities);
        List<PatientResource> patients = patientService.getAll();

        // Then
        assertEquals(3, patients.size());
        assertEquals(3l, patients.get(2).id());
    }

    @Test
    void testCreate() {
        // Given
        PatientResource resource = PatientResource.builder().name("Patient").build();

        // When
        when(patientRepository.save(any())).then(args -> {
            PatientEntity entity = args.getArgument(0, PatientEntity.class);
            entity.setId(123l);
            return entity;
        });
        PatientResource patient = patientService.create(resource);
        // Then
        assertEquals("Patient", patient.name());
        assertNotNull(patient.id());
    }

    @Test
    void testCreateThrowsIllegalArgumentException() {
        // Given
        PatientResource resource = PatientResource.builder().id(123l).name("Patient").build();

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> patientService.create(resource));
    }

    @Test
    void testCreateFromRequest() {
        // Given
        CreatePatientRequest request = CreatePatientRequest.builder()
                .name("new patient")
                .build();


        // When
        when(patientRepository.save(any())).then(args -> {
            PatientEntity entity = args.getArgument(0, PatientEntity.class);
            entity.setId(123l);
            return entity;
        });
        PatientResource patient = patientService.create(request);
        // Then
        assertEquals("new patient", patient.name());
        assertNotNull(patient.id());
    }

    @Test
    void testUpdate() {
        // Given
        PatientResource resource = PatientResource.builder().id(123456l).name("Patient").build();

        // When
        when(patientRepository.existById(123456l)).thenReturn(true);
        when(patientRepository.save(any())).then(args -> args.getArgument(0, PatientEntity.class));
        PatientResource patient = patientService.update(resource);
        // Then
        assertEquals("Patient", patient.name());
        assertEquals(123456l, patient.id());
    }

    @Test
    void testUpdateThrowsNotFoundException() {
        // Given
        PatientResource resource = PatientResource.builder().id(123456l).name("Patient").build();

        // When
        when(patientRepository.existById(123456l)).thenReturn(false);
        when(patientRepository.save(any())).then(args -> args.getArgument(0, PatientEntity.class));

        // Then
        assertThrows(NotFoundException.class, () -> patientService.update(resource));
    }

    @Test
    void testDelete() {
        // Given
        Long id = 123789l;

        // When
        when(patientRepository.existById(id)).thenReturn(true);
        patientService.delete(id);

        // Then
        verify(patientRepository, times(1)).delete(id);
    }

    @Test
    void testDeleteThrowsNotFoundException() {
        // Given
        Long id = 123789l;

        // When
        when(patientRepository.existById(id)).thenReturn(false);

        // Then
        assertThrows(NotFoundException.class, () -> patientService.delete(id));
    }
}
