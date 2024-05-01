package com.mydoctor.application.service;

import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.application.exception.BusinessException;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.resource.DoctorResource;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.application.resource.PatientResource;
import com.mydoctor.application.resource.WorkingIntervalResource;
import com.mydoctor.infrastructure.entity.DoctorEntity;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.entity.PatientEntity;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import com.mydoctor.presentation.request.create.CreatePatientRequest;
import com.mydoctor.presentation.request.create.CreateWorkingIntervalRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkingIntervalServiceTest {

    @Mock
    private WorkingIntervalRepositoryAdapter workingIntervalRepository;
    @Mock
    private DoctorService doctorService;
    @Mock
    private MedicalOfficeService medicalOfficeService;

    private WorkingIntervalService workingIntervalService;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        this.workingIntervalService = new WorkingIntervalService(workingIntervalRepository, doctorService, medicalOfficeService);
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
        WorkingIntervalResource resource = WorkingIntervalResource.builder()
                .medicalOffice(MedicalOfficeResource.builder().id(1l).build())
                .doctor(DoctorResource.builder().id(1l).build())
                .date(LocalDate.of(2024, 4, 7))
                .start(LocalTime.of(8, 0))
                .end(LocalTime.of(12, 0))
                .build();

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
    void testCreateFromRequest() {
        // Given
        CreateWorkingIntervalRequest request = CreateWorkingIntervalRequest.builder()
                .date(LocalDate.of(2024, 5, 1))
                .start(LocalTime.of(8, 0))
                .end(LocalTime.of(12, 0))
                .doctorId(1l)
                .medicalOfficeId(1l)
                .build();
        DoctorResource doctorResource = DoctorResource.builder()
                .id(1l)
                .build();
        MedicalOfficeResource medicalOfficeResource = MedicalOfficeResource.builder()
                .id(1l)
                .build();

        // When
        when(doctorService.get(1l)).thenReturn(doctorResource);
        when(medicalOfficeService.get(1l)).thenReturn(medicalOfficeResource);
        when(workingIntervalRepository.save(any())).then(args -> {
            WorkingIntervalEntity entity = args.getArgument(0, WorkingIntervalEntity.class);
            entity.setId(123l);
            return entity;
        });
        WorkingIntervalResource workingInterval = workingIntervalService.create(request);
        // Then
        assertEquals(LocalDate.of(2024, 5, 1), workingInterval.date());
        assertEquals(LocalTime.of(12, 0), workingInterval.end());
        assertEquals(1l, workingInterval.doctor().id());
        assertNotNull(workingInterval.id());
    }

    @Test
    void testCreateShouldThrowExceptionIfConflict() {
        // Given
        WorkingIntervalEntity existingWorkingInterval = WorkingIntervalEntity.builder()
                .id(1l)
                .medicalOffice(MedicalOfficeEntity.builder().id(1l).build())
                .doctor(DoctorEntity.builder().id(2l).build())
                .date(LocalDate.of(2024, 5, 1))
                .start(LocalTime.of(14, 0))
                .end(LocalTime.of(18, 0))
                .build();

        WorkingIntervalResource newWorkingInterval = WorkingIntervalResource.builder()
                .date(LocalDate.of(2024, 5, 1))
                .start(LocalTime.of(8, 0))
                .end(LocalTime.of(14, 1))
                .medicalOffice(MedicalOfficeResource.builder().id(1l).build())
                .doctor(DoctorResource.builder().id(2l).build())
                .build();

        // When
        when(workingIntervalRepository
                .get(1l, 2l, LocalDate.of(2024, 5, 1)))
                .thenReturn(List.of(existingWorkingInterval));

        // Then
        assertThrows(BusinessException.class, () -> workingIntervalService.create(newWorkingInterval));
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
