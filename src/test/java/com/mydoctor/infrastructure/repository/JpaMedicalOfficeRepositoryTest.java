package com.mydoctor.infrastructure.repository;

import com.mydoctor.infrastructure.entity.CityEntity;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.entity.SpecializationEntity;
import com.mydoctor.application.command.MedicalOfficeSearchCriteriaCommand;
import com.mydoctor.infrastructure.repository.specification.MedicalOfficeSpecificationBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class JpaMedicalOfficeRepositoryTest {

    @Autowired
    private MedicalOfficeRepository medicalOfficeRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Test
    // Start new context for every test
    @DirtiesContext
    public void testFindAllWithCityId() {
        // Create and save some dummy data
        CityEntity city1 = new CityEntity(1L, "City1");
        cityRepository.save(city1);

        MedicalOfficeEntity medicalOffice1 = new MedicalOfficeEntity();
        medicalOffice1.setId(1L);
        medicalOffice1.setName("Medical Office 1");
        medicalOffice1.setCity(city1);
        medicalOfficeRepository.save(medicalOffice1);

        // Define search criteria by city
        MedicalOfficeSearchCriteriaCommand criteria = new MedicalOfficeSearchCriteriaCommand();
        criteria.setCityId(1L); // Search for medical offices in City1

        // Execute search
        Specification<MedicalOfficeEntity> spec = new MedicalOfficeSpecificationBuilder().with(criteria).build();
        List<MedicalOfficeEntity> medicalOffices = medicalOfficeRepository.findAll(spec);

        // Assertions
        assertThat(medicalOffices).isNotNull();
        assertThat(medicalOffices).hasSize(1);
        assertThat(medicalOffices.get(0).getName()).isEqualTo("Medical Office 1");
        assertThat(medicalOffices.get(0).getCity().getName()).isEqualTo("City1");
    }

    @Test
    @DirtiesContext
    public void testFindAllWithSpecializationId() {
        // Create and save some dummy data
        CityEntity city1 = new CityEntity(1L, "City1");
        cityRepository.save(city1);

        SpecializationEntity specialization1 = new SpecializationEntity(1L, "Specialization1");
        specializationRepository.save(specialization1);

        MedicalOfficeEntity medicalOffice1 = new MedicalOfficeEntity();
        medicalOffice1.setId(1L);
        medicalOffice1.setName("Medical Office 1");
        medicalOffice1.setCity(city1);
        medicalOffice1.getSpecializations().add(specialization1);
        medicalOfficeRepository.save(medicalOffice1);

        // Define search criteria by specialization
        MedicalOfficeSearchCriteriaCommand criteria = new MedicalOfficeSearchCriteriaCommand();
        criteria.setSpecializationId(1L); // Search for medical offices with Specialization1

        // Execute search
        Specification<MedicalOfficeEntity> spec = new MedicalOfficeSpecificationBuilder().with(criteria).build();
        List<MedicalOfficeEntity> medicalOffices = medicalOfficeRepository.findAll(spec);

        // Assertions
        assertThat(medicalOffices).isNotNull();
        assertThat(medicalOffices).hasSize(1);
        assertThat(medicalOffices.get(0).getName()).isEqualTo("Medical Office 1");
    }

    @Test
    @DirtiesContext
    public void testFindAllWithCityIdAndSpecializationId() {
        // Create and save some dummy data
        CityEntity city1 = new CityEntity(1L, "City1");
        cityRepository.save(city1);

        SpecializationEntity specialization1 = new SpecializationEntity(1L, "Specialization1");
        specializationRepository.save(specialization1);

        MedicalOfficeEntity medicalOffice1 = new MedicalOfficeEntity();
        medicalOffice1.setId(1L);
        medicalOffice1.setName("Medical Office 1");
        medicalOffice1.setCity(city1);
        medicalOffice1.getSpecializations().add(specialization1);
        medicalOfficeRepository.save(medicalOffice1);

        // Define search criteria by city and specialization
        MedicalOfficeSearchCriteriaCommand criteria = new MedicalOfficeSearchCriteriaCommand();
        criteria.setCityId(1L); // Search for medical offices in City1
        criteria.setSpecializationId(1L); // Search for medical offices with Specialization1

        // Execute search
        Specification<MedicalOfficeEntity> spec = new MedicalOfficeSpecificationBuilder().with(criteria).build();
        List<MedicalOfficeEntity> medicalOffices = medicalOfficeRepository.findAll(spec);

        // Assertions
        assertThat(medicalOffices).isNotNull();
        assertThat(medicalOffices).hasSize(1);
        assertThat(medicalOffices.get(0).getName()).isEqualTo("Medical Office 1");
    }

    @Test
    @DirtiesContext
    public void testFindAllWithName() {
        // Create and save some dummy data
        CityEntity city1 = new CityEntity(1L, "City1");
        cityRepository.save(city1);

        MedicalOfficeEntity medicalOffice1 = new MedicalOfficeEntity();
        medicalOffice1.setId(1L);
        medicalOffice1.setName("Medical Office ABC");
        medicalOffice1.setCity(city1);
        medicalOfficeRepository.save(medicalOffice1);

        MedicalOfficeEntity medicalOffice2 = new MedicalOfficeEntity();
        medicalOffice2.setId(2L);
        medicalOffice2.setName("XYZ Medical Office");
        medicalOffice2.setCity(city1);
        medicalOfficeRepository.save(medicalOffice2);

        // Define search criteria by name
        MedicalOfficeSearchCriteriaCommand criteria = new MedicalOfficeSearchCriteriaCommand();
        criteria.setName("ABC"); // Search for medical offices with name containing "ABC"

        // Execute search
        Specification<MedicalOfficeEntity> spec = new MedicalOfficeSpecificationBuilder().with(criteria).build();
        List<MedicalOfficeEntity> medicalOffices = medicalOfficeRepository.findAll(spec);

        // Assertions
        assertThat(medicalOffices).isNotNull();
        assertThat(medicalOffices).hasSize(1);
        assertThat(medicalOffices.get(0).getName()).isEqualTo("Medical Office ABC");
    }
}

