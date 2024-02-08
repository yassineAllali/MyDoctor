package com.mydoctor.infrastructure.repository;

import com.mydoctor.infrastructure.entity.CityEntity;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.entity.SpecializationEntity;
import com.mydoctor.infrastructure.repository.criteria.MedicalOfficeSearchCriteria;
import com.mydoctor.infrastructure.repository.criteria.MedicalOfficeSpecificationBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JpaMedicalOfficeRepositoryTest {

    @Autowired
    private MedicalOfficeRepository medicalOfficeRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Test
    public void testFindAllWithSpecification() {
        // Create and save some dummy data
        CityEntity city1 = new CityEntity(1L, "City1");
        cityRepository.save(city1);

        CityEntity city2 = new CityEntity(2L, "City2");
        cityRepository.save(city2);

        SpecializationEntity specialization1 = new SpecializationEntity(1L, "Specialization1");
        specializationRepository.save(specialization1);

        SpecializationEntity specialization2 = new SpecializationEntity(2L, "Specialization2");
        specializationRepository.save(specialization2);

        MedicalOfficeEntity medicalOffice1 = new MedicalOfficeEntity();
        medicalOffice1.setId(1L);
        medicalOffice1.setName("Medical Office 1");
        medicalOffice1.setCity(city1);
        medicalOffice1.getSpecializations().add(specialization1);
        medicalOfficeRepository.save(medicalOffice1);

        MedicalOfficeEntity medicalOffice2 = new MedicalOfficeEntity();
        medicalOffice2.setId(2L);
        medicalOffice2.setName("Medical Office 2");
        medicalOffice2.setCity(city2);
        medicalOffice2.getSpecializations().add(specialization2);
        medicalOfficeRepository.save(medicalOffice2);

        // Define search criteria
        MedicalOfficeSearchCriteria criteria = new MedicalOfficeSearchCriteria();
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
}

