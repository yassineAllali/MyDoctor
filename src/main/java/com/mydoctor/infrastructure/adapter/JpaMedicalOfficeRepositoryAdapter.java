package com.mydoctor.infrastructure.adapter;

import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.infrastructure.repository.MedicalOfficeRepository;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.repository.criteria.MedicalOfficeSearchCriteria;
import com.mydoctor.infrastructure.repository.criteria.MedicalOfficeSpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Repository
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

    @Override
    public List<MedicalOfficeEntity> get(MedicalOfficeSearchCriteria criteria) {
        Specification<MedicalOfficeEntity> spec = new MedicalOfficeSpecificationBuilder().with(criteria).build();
        return repository.findAll(spec);
    }
}
