package com.mydoctor.application.service;

import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.command.MedicalOfficeSearchCriteriaCommand;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class MedicalOfficeServiceTest {

    private MedicalOfficeService medicalOfficeService;

    @Mock
    private MedicalOfficeRepositoryAdapter medicalOfficeRepository;

    private AutoCloseable openMocks;
    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        medicalOfficeService = new MedicalOfficeService(medicalOfficeRepository);
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

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

}