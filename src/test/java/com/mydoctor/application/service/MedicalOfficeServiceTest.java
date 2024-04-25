package com.mydoctor.application.service;

import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.command.MedicalOfficeSearchCriteriaCommand;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.resource.CityResource;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.application.resource.PatientResource;
import com.mydoctor.application.resource.SpecializationResource;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.entity.PatientEntity;
import com.mydoctor.presentation.request.create.CreateMedicalOfficeRequest;
import com.mydoctor.presentation.request.create.CreatePatientRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class MedicalOfficeServiceTest {

    private MedicalOfficeService medicalOfficeService;

    @Mock
    private MedicalOfficeRepositoryAdapter medicalOfficeRepository;
    @Mock
    private SpecializationService specializationService;
    @Mock
    private CityService cityService;

    private AutoCloseable openMocks;
    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        medicalOfficeService = new MedicalOfficeService(medicalOfficeRepository, specializationService, cityService);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    public void testGetMedicalOfficeByCriteria() {
        // Given
        MedicalOfficeSearchCriteriaCommand searchCriteria = new MedicalOfficeSearchCriteriaCommand();
        searchCriteria.setName("Med");
        searchCriteria.setCityId(1L);

        List<MedicalOfficeEntity> entities = List.of(
                new MedicalOfficeEntity(1L, "Med office 1", null, null),
                new MedicalOfficeEntity(2L, "Med office 2", null, null)
        );

        // When
        when(medicalOfficeRepository.get(searchCriteria)).thenReturn(entities);
        List<MedicalOfficeResource> actualMedicalOffices = medicalOfficeService.get(searchCriteria);

        // Then
        assertEquals(2, actualMedicalOffices.size());
    }

    @Test
    void testGet() {
        // Given
        MedicalOfficeEntity medicalOfficeEntity = MedicalOfficeEntity.builder().id(123456l).build();
        Long id = 123l;

        // When
        when(medicalOfficeRepository.get(id)).thenReturn(Optional.of(medicalOfficeEntity));
        MedicalOfficeResource medicalOfficeResource = medicalOfficeService.get(id);

        // Then
        assertEquals(123456l, medicalOfficeResource.id());
    }

    @Test
    void testGetThrowsNotFoundException() {
        // Given
        Long id = 123l;

        // When
        when(medicalOfficeRepository.get(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(NotFoundException.class, () -> medicalOfficeService.get(id));
    }

    @Test
    void testGetAll() {
        // Given
        List<MedicalOfficeEntity> entities = List.of(
                MedicalOfficeEntity.builder().id(1l).build(),
                MedicalOfficeEntity.builder().id(2l).build(),
                MedicalOfficeEntity.builder().id(3l).build()
        );

        // When
        when(medicalOfficeRepository.getAll()).thenReturn(entities);
        List<MedicalOfficeResource> medicalOffices = medicalOfficeService.getAll();

        // Then
        assertEquals(3, medicalOffices.size());
        assertEquals(3l, medicalOffices.get(2).id());
    }

    @Test
    void testCreate() {
        // Given
        MedicalOfficeResource resource = MedicalOfficeResource.builder().name("MedicalOffice").build();

        // When
        when(medicalOfficeRepository.save(any())).then(args -> {
            MedicalOfficeEntity entity = args.getArgument(0, MedicalOfficeEntity.class);
            entity.setId(123l);
            return entity;
        });
        MedicalOfficeResource medicalOffice = medicalOfficeService.create(resource);
        // Then
        assertEquals("MedicalOffice", medicalOffice.name());
        assertNotNull(medicalOffice.id());
    }

    @Test
    void testCreateThrowsIllegalArgumentException() {
        // Given
        MedicalOfficeResource resource = MedicalOfficeResource.builder().id(123l).name("MedicalOffice").build();

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> medicalOfficeService.create(resource));
    }

    @Test
    void testCreateFromRequest() {
        // Given
        CreateMedicalOfficeRequest request = CreateMedicalOfficeRequest.builder()
                .name("new med office")
                .cityId(1l)
                .specializationIds(Set.of(1l, 2l))
                .build();
        CityResource cityResource = CityResource.builder()
                .id(1l)
                .name("Fes")
                .build();

        Set<SpecializationResource> specializationResources = Set.of(
                SpecializationResource.builder()
                        .id(1l)
                        .name("sp1")
                        .build(),
                SpecializationResource.builder()
                        .id(2l)
                        .name("sp2")
                        .build()
        );

        // When
        when(cityService.get(1l)).thenReturn(cityResource);
        when(specializationService.get(Set.of(1l, 2l))).thenReturn(specializationResources);

        when(medicalOfficeRepository.save(any())).then(args -> {
            MedicalOfficeEntity entity = args.getArgument(0, MedicalOfficeEntity.class);
            entity.setId(123l);
            return entity;
        });
        MedicalOfficeResource medicalOffice = medicalOfficeService.create(request);
        // Then
        assertEquals("new med office", medicalOffice.name());
        assertEquals(2, medicalOffice.specializations().size());
        assertEquals("Fes", medicalOffice.city().name());
        assertNotNull(medicalOffice.id());
    }

    @Test
    void testUpdate() {
        // Given
        MedicalOfficeResource resource = MedicalOfficeResource.builder().id(123456l).name("MedicalOffice").build();

        // When
        when(medicalOfficeRepository.existById(123456l)).thenReturn(true);
        when(medicalOfficeRepository.save(any())).then(args -> args.getArgument(0, MedicalOfficeEntity.class));
        MedicalOfficeResource medicalOffice = medicalOfficeService.update(resource);
        // Then
        assertEquals("MedicalOffice", medicalOffice.name());
        assertEquals(123456l, medicalOffice.id());
    }

    @Test
    void testUpdateThrowsNotFoundException() {
        // Given
        MedicalOfficeResource resource = MedicalOfficeResource.builder().id(123456l).name("MedicalOffice").build();

        // When
        when(medicalOfficeRepository.existById(123456l)).thenReturn(false);
        when(medicalOfficeRepository.save(any())).then(args -> args.getArgument(0, MedicalOfficeEntity.class));

        // Then
        assertThrows(NotFoundException.class, () -> medicalOfficeService.update(resource));
    }

    @Test
    void testDelete() {
        // Given
        Long id = 123789l;

        // When
        when(medicalOfficeRepository.existById(id)).thenReturn(true);
        medicalOfficeService.delete(id);

        // Then
        verify(medicalOfficeRepository, times(1)).delete(id);
    }

    @Test
    void testDeleteThrowsNotFoundException() {
        // Given
        Long id = 123789l;

        // When
        when(medicalOfficeRepository.existById(id)).thenReturn(false);

        // Then
        assertThrows(NotFoundException.class, () -> medicalOfficeService.delete(id));
    }

}