package com.mydoctor.infrastructure.adapter;

import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.infrastructure.repository.MedicalOfficeRepository;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public class JpaMedicalOfficeRepositoryAdapter implements MedicalOfficeRepositoryAdapter {

    private final MedicalOfficeRepository repository;

    public JpaMedicalOfficeRepositoryAdapter(MedicalOfficeRepository repository) {
        this.repository = repository;
    }

    @Override
    public MedicalOfficeEntity save(MedicalOfficeEntity medicalOfficeEntity) {
        return repository.save(medicalOfficeEntity);
    }

    @Override
    public Optional<MedicalOfficeEntity> get(Long medicalOfficeId) {
        return repository.findById(medicalOfficeId);
    }
}
