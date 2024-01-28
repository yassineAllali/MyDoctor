package com.mydoctor.infrastructure.adapter.dummy;

import com.mydoctor.application.adapter.PatientRepositoryAdapter;
import com.mydoctor.infrastructure.entity.PatientEntity;
import com.mydoctor.infrastructure.repository.PatientRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DummyPatientRepositoryAdapter implements PatientRepositoryAdapter {

    private final List<PatientEntity> entities;

    private List<PatientEntity> generateEntities() {
        List<PatientEntity> patientEntities = new ArrayList<>(20);
        List<String> personNames = List.of(
                "Alice", "Bob", "Charlie", "David", "Emily",
                "Frank", "Grace", "Henry", "Isabel", "Jack",
                "Katherine", "Liam", "Mia", "Nathan", "Olivia",
                "Paul", "Quinn", "Ryan", "Sophia", "Tyler"
        );
        for(int i = 0; i < patientEntities.size(); i++) {
            patientEntities.add(new PatientEntity((long)i + 1, personNames.get(i)));
        }
        return patientEntities;
    }

    public DummyPatientRepositoryAdapter(PatientRepository repository) {
        entities = generateEntities();
    }

    @Override
    public PatientEntity save(PatientEntity patientEntity) {
        if(patientEntity.getId() == null
                || entities.stream().noneMatch(m -> m.getId().equals(patientEntity.getId()))) {
            patientEntity.setId((long)entities.size() + 1);
            entities.add(patientEntity);
        }
        return patientEntity;
    }

    @Override
    public Optional<PatientEntity> get(long id) {
        return entities.stream().filter(e -> e.getId().equals(id)).findFirst();
    }
}
