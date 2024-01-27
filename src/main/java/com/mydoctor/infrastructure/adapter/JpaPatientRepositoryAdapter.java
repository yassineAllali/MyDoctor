package com.mydoctor.infrastructure.adapter;

import com.mydoctor.application.adapter.PatientRepositoryAdapter;
import com.mydoctor.infrastructure.repository.PatientRepository;
import com.mydoctor.infrastructure.entity.PatientEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//@Repository
public class JpaPatientRepositoryAdapter implements PatientRepositoryAdapter {

    private final PatientRepository repository;

    public JpaPatientRepositoryAdapter(PatientRepository repository) {
        this.repository = repository;
    }

    @Override
    public PatientEntity save(PatientEntity patientEntity) {
        return repository.save(patientEntity);
    }
}
