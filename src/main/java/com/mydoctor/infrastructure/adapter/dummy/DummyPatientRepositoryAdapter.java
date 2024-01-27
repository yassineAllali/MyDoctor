package com.mydoctor.infrastructure.adapter.dummy;

import com.mydoctor.application.adapter.PatientRepositoryAdapter;
import com.mydoctor.infrastructure.entity.PatientEntity;
import com.mydoctor.infrastructure.repository.PatientRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DummyPatientRepositoryAdapter implements PatientRepositoryAdapter {

    private final PatientRepository repository;

    public DummyPatientRepositoryAdapter(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public PatientEntity save(PatientEntity patientEntity) {
        return repository.save(patientEntity);
    }
}
