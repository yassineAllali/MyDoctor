package com.mydoctor.infrastructure.adapter;

import com.mydoctor.application.adapter.PatientRepositoryAdapter;
import com.mydoctor.application.resource.PatientResource;
import com.mydoctor.infrastructure.repository.PatientRepository;
import com.mydoctor.infrastructure.mapper.ApplicationMapper;
import com.mydoctor.infrastructure.entity.PatientEntity;
import org.springframework.stereotype.Service;

@Service
public class JpaPatientRepositoryAdapter implements PatientRepositoryAdapter {

    private final PatientRepository repository;
    private final ApplicationMapper mapper;

    public JpaPatientRepositoryAdapter(PatientRepository repository) {
        this.repository = repository;
        this.mapper = new ApplicationMapper();;
    }

    @Override
    public PatientResource save(PatientResource patientResource) {
        PatientEntity entity = mapper.map(patientResource);
        return mapper.map(repository.save(entity));
    }
}
