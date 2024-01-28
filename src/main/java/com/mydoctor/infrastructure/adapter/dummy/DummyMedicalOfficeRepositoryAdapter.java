package com.mydoctor.infrastructure.adapter.dummy;

import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.repository.MedicalOfficeRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class DummyMedicalOfficeRepositoryAdapter implements MedicalOfficeRepositoryAdapter {

    private final List<MedicalOfficeEntity> medicalOfficeEntities;

    private List<MedicalOfficeEntity> generateDummyMedicalOffices() {
        List<String> names = Arrays.asList(
                "General Hospital",
                "City Medical Center",
                "Regional Health Center",
                "Community Hospital",
                "University Medical Center",
                "Mercy Hospital",
                "Sunset Healthcare",
                "Metro Health Services",
                "Riverside Medical Clinic",
                "Unity Medical Center"
        );
        List<MedicalOfficeEntity> entities = new ArrayList<>(10);
        for(int i = 0; i < names.size(); i++) {
            entities.add(new MedicalOfficeEntity((long) i + 1, names.get(i)));
        }
        return entities;
    }


    public DummyMedicalOfficeRepositoryAdapter() {
        medicalOfficeEntities = generateDummyMedicalOffices();
    }

    @Override
    public MedicalOfficeEntity save(MedicalOfficeEntity medicalOfficeEntity) {
        if(medicalOfficeEntity.getId() == null
                || medicalOfficeEntities.stream().noneMatch(m -> m.getId().equals(medicalOfficeEntity.getId()))) {
            medicalOfficeEntity.setId((long)medicalOfficeEntities.size() + 1);
            medicalOfficeEntities.add(medicalOfficeEntity);
        }
        return medicalOfficeEntity;
    }

    @Override
    public Optional<MedicalOfficeEntity> get(Long medicalOfficeId) {
        return medicalOfficeEntities.stream().filter(m -> m.getId().equals(medicalOfficeId)).findFirst();
    }
}
