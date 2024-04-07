package com.mydoctor.application.service;

import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.resource.WorkingIntervalResource;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkingIntervalServiceTest {

    @Mock
    private WorkingIntervalRepositoryAdapter workingIntervalRepository;

    private WorkingIntervalService workingIntervalService;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        this.workingIntervalService = new WorkingIntervalService(workingIntervalRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testGet() {
        // Given
        WorkingIntervalEntity workingIntervalEntity = WorkingIntervalEntity.builder().id(123456l).build();

        // When
        when(workingIntervalRepository.get(123456l)).thenReturn(Optional.of(workingIntervalEntity));
        WorkingIntervalResource workingIntervalResource = workingIntervalService.get(123456l);

        // Then
        assertEquals(123456l, workingIntervalResource.id());
    }

    @Test
    void testGetThrowsNotFoundException() {
        // Given
        Long id = 123l;

        // When
        when(workingIntervalRepository.get(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> workingIntervalService.get(id));
    }

    @Test
    void testGetAll() {
        // Given
        List<WorkingIntervalEntity> entities = List.of(
                WorkingIntervalEntity.builder().id(1l).build(),
                WorkingIntervalEntity.builder().id(2l).build(),
                WorkingIntervalEntity.builder().id(3l).build()
        );

        // When
        when(workingIntervalRepository.getAll()).thenReturn(entities);
        List<WorkingIntervalResource> workingIntervals = workingIntervalService.getAll();

        // Then
        assertEquals(3, workingIntervals.size());
        assertEquals(3l, workingIntervals.get(2).id());
    }

    @Test
    void testCreate() {
        // Given
        WorkingIntervalResource resource = WorkingIntervalResource.builder().date(LocalDate.of(2024, 4, 7)).build();

        // When
        when(workingIntervalRepository.save(any())).then(args -> {
            WorkingIntervalEntity entity = args.getArgument(0, WorkingIntervalEntity.class);
            entity.setId(123l);
            return entity;
        });
        WorkingIntervalResource workingInterval = workingIntervalService.create(resource);
        // Then
        assertEquals(LocalDate.of(2024, 4, 7), workingInterval.date());
        assertNotNull(workingInterval.id());
    }

    @Test
    void testCreateThrowsIllegalArgumentException() {
        // Given
        WorkingIntervalResource resource = WorkingIntervalResource.builder().id(123l).date(LocalDate.of(2024, 4, 7)).build();

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> workingIntervalService.create(resource));
    }

    @Test
    void testUpdate() {
        // Given
        WorkingIntervalResource resource = WorkingIntervalResource.builder().id(123456l).date(LocalDate.of(2024, 4, 7)).build();

        // When
        when(workingIntervalRepository.existById(123456l)).thenReturn(true);
        when(workingIntervalRepository.save(any())).then(args -> args.getArgument(0, WorkingIntervalEntity.class));
        WorkingIntervalResource workingInterval = workingIntervalService.update(resource);
        // Then
        assertEquals(LocalDate.of(2024, 4, 7), workingInterval.date());
        assertEquals(123456l, workingInterval.id());
    }

    @Test
    void testUpdateThrowsNotFoundException() {
        // Given
        WorkingIntervalResource resource = WorkingIntervalResource.builder().id(123456l).date(LocalDate.of(2024, 4, 7)).build();

        // When
        when(workingIntervalRepository.existById(123456l)).thenReturn(false);
        when(workingIntervalRepository.save(any())).then(args -> args.getArgument(0, WorkingIntervalEntity.class));

        // Then
        assertThrows(NotFoundException.class, () -> workingIntervalService.update(resource));
    }

    @Test
    void testDelete() {
        // Given
        Long id = 123789l;

        // When
        when(workingIntervalRepository.existById(id)).thenReturn(true);
        workingIntervalService.delete(id);

        // Then
        verify(workingIntervalRepository, times(1)).delete(id);
    }

    @Test
    void testDeleteThrowsNotFoundException() {
        // Given
        Long id = 123789l;

        // When
        when(workingIntervalRepository.existById(id)).thenReturn(false);

        // Then
        assertThrows(NotFoundException.class, () -> workingIntervalService.delete(id));
    }
}
