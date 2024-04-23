package com.mydoctor.application.service;

import com.mydoctor.application.adapter.DoctorRepositoryAdapter;
import com.mydoctor.application.exception.IllegalArgumentException;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.resource.DoctorResource;
import com.mydoctor.application.resource.SpecializationResource;
import com.mydoctor.infrastructure.entity.DoctorEntity;
import com.mydoctor.presentation.request.create.CreateDoctorRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceTest {

    @Mock
    private DoctorRepositoryAdapter doctorRepository;

    @Mock
    private SpecializationService specializationService;

    private DoctorService doctorService;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        this.doctorService = new DoctorService(doctorRepository, specializationService);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testGet() {
        // Given
        DoctorEntity doctorEntity = DoctorEntity.builder().id(123456l).build();
        Long id = 123l;

        // When
        when(doctorRepository.get(id)).thenReturn(Optional.of(doctorEntity));
        DoctorResource doctorResource = doctorService.get(id);

        // Then
        assertEquals(123456l, doctorResource.id());
    }

    @Test
    void testGetThrowsNotFoundException() {
        // Given
        Long id = 123l;

        // When
        when(doctorRepository.get(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> doctorService.get(id));
    }

    @Test
    void testGetAll() {
        // Given
        List<DoctorEntity> entities = List.of(
                DoctorEntity.builder().id(1l).build(),
                DoctorEntity.builder().id(2l).build(),
                DoctorEntity.builder().id(3l).build()
        );

        // When
        when(doctorRepository.getAll()).thenReturn(entities);
        List<DoctorResource> doctors = doctorService.getAll();

        // Then
        assertEquals(3, doctors.size());
        assertEquals(3l, doctors.get(2).id());
    }

    @Test
    void testCreate() {
        // Given
        DoctorResource resource = DoctorResource.builder().name("Doctor").build();

        // When
        when(doctorRepository.save(any())).then(args -> {
            DoctorEntity entity = args.getArgument(0, DoctorEntity.class);
            entity.setId(123l);
            return entity;
        });
        DoctorResource doctor = doctorService.create(resource);
        // Then
        assertEquals("Doctor", doctor.name());
        assertNotNull(doctor.id());
    }

    @Test
    void testCreateThrowsIllegalArgumentException() {
        // Given
        DoctorResource resource = DoctorResource.builder().id(123l).name("Doctor").build();

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> doctorService.create(resource));
    }

    @Test
    void testCreateDoctorFromRequest() {
        // Given
        SpecializationResource givenSpecialization = SpecializationResource.builder()
                .id(123l)
                .name("general")
                .build();
        CreateDoctorRequest request = CreateDoctorRequest.builder()
                .name("new doctor")
                .specializationId(123l)
                .build();


        // When
        when(specializationService.get(123l)).thenReturn(givenSpecialization);
        when(doctorRepository.save(any())).then(args -> {
            DoctorEntity entity = args.getArgument(0, DoctorEntity.class);
            entity.setId(123l);
            return entity;
        });
        DoctorResource doctor = doctorService.create(request);
        // Then
        assertEquals("new doctor", doctor.name());
        assertEquals("general", doctor.specialization().name());
        assertNotNull(doctor.id());
    }

    @Test
    void testUpdate() {
        // Given
        DoctorResource resource = DoctorResource.builder().id(123456l).name("Doctor").build();

        // When
        when(doctorRepository.existById(123456l)).thenReturn(true);
        when(doctorRepository.save(any())).then(args -> args.getArgument(0, DoctorEntity.class));
        DoctorResource doctor = doctorService.update(resource);
        // Then
        assertEquals("Doctor", doctor.name());
        assertEquals(123456l, doctor.id());
    }

    @Test
    void testUpdateThrowsNotFoundException() {
        // Given
        DoctorResource resource = DoctorResource.builder().id(123456l).name("Doctor").build();

        // When
        when(doctorRepository.existById(123456l)).thenReturn(false);
        when(doctorRepository.save(any())).then(args -> args.getArgument(0, DoctorEntity.class));

        // Then
        assertThrows(NotFoundException.class, () -> doctorService.update(resource));
    }

    @Test
    void testDelete() {
        // Given
        Long id = 123789l;

        // When
        when(doctorRepository.existById(id)).thenReturn(true);
        doctorService.delete(id);

        // Then
        verify(doctorRepository, times(1)).delete(id);
    }

    @Test
    void testDeleteThrowsNotFoundException() {
        // Given
        Long id = 123789l;

        // When
        when(doctorRepository.existById(id)).thenReturn(false);

        // Then
        assertThrows(NotFoundException.class, () -> doctorService.delete(id));
    }
}