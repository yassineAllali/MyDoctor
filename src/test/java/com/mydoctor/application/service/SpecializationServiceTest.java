package com.mydoctor.application.service;

import com.mydoctor.application.adapter.SpecializationRepositoryAdapter;
import com.mydoctor.application.exception.IllegalArgumentException;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.resource.SpecializationResource;
import com.mydoctor.infrastructure.entity.SpecializationEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpecializationServiceTest {

    @Mock
    private SpecializationRepositoryAdapter specializationRepository;

    private SpecializationService specializationService;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        this.specializationService = new SpecializationService(specializationRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testGet() {
        // Given
        SpecializationEntity specializationEntity = SpecializationEntity.builder().id(123456L).build();

        // When
        when(specializationRepository.get(123456L)).thenReturn(Optional.of(specializationEntity));
        SpecializationResource specializationResource = specializationService.get(123456L);

        // Then
        assertEquals(123456L, specializationResource.id());
    }

    @Test
    void testGetThrowsNotFoundException() {
        // Given
        Long id = 123L;

        // When
        when(specializationRepository.get(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> specializationService.get(id));
    }

    @Test
    void testGetAll() {
        // Given
        List<SpecializationEntity> entities = List.of(
                SpecializationEntity.builder().id(1L).build(),
                SpecializationEntity.builder().id(2L).build(),
                SpecializationEntity.builder().id(3L).build()
        );

        // When
        when(specializationRepository.getAll()).thenReturn(entities);
        List<SpecializationResource> specializations = specializationService.getAll();

        // Then
        assertEquals(3, specializations.size());
        assertEquals(3L, specializations.get(2).id());
    }

    @Test
    void testCreate() {
        // Given
        SpecializationResource resource = SpecializationResource.builder().name("Specialization").build();

        // When
        when(specializationRepository.save(any())).then(args -> {
            SpecializationEntity entity = args.getArgument(0, SpecializationEntity.class);
            entity.setId(123L);
            return entity;
        });
        SpecializationResource specialization = specializationService.create(resource);

        // Then
        assertEquals("Specialization", specialization.name());
        assertNotNull(specialization.id());
    }

    @Test
    void testCreateThrowsIllegalArgumentException() {
        // Given
        SpecializationResource resource = SpecializationResource.builder().id(123L).name("Specialization").build();

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> specializationService.create(resource));
    }

    @Test
    void testUpdate() {
        // Given
        SpecializationResource resource = SpecializationResource.builder().id(123456L).name("Specialization").build();

        // When
        when(specializationRepository.existById(123456L)).thenReturn(true);
        when(specializationRepository.save(any())).then(args -> args.getArgument(0, SpecializationEntity.class));
        SpecializationResource specialization = specializationService.update(resource);

        // Then
        assertEquals("Specialization", specialization.name());
        assertEquals(123456L, specialization.id());
    }

    @Test
    void testUpdateThrowsNotFoundException() {
        // Given
        SpecializationResource resource = SpecializationResource.builder().id(123456L).name("Specialization").build();

        // When
        when(specializationRepository.existById(123456L)).thenReturn(false);
        when(specializationRepository.save(any())).then(args -> args.getArgument(0, SpecializationEntity.class));

        // Then
        assertThrows(NotFoundException.class, () -> specializationService.update(resource));
    }

    @Test
    void testDelete() {
        // Given
        Long id = 123789L;

        // When
        when(specializationRepository.existById(id)).thenReturn(true);
        specializationService.delete(id);

        // Then
        verify(specializationRepository, times(1)).delete(id);
    }

    @Test
    void testDeleteThrowsNotFoundException() {
        // Given
        Long id = 123789L;

        // When
        when(specializationRepository.existById(id)).thenReturn(false);

        // Then
        assertThrows(NotFoundException.class, () -> specializationService.delete(id));
    }
}
