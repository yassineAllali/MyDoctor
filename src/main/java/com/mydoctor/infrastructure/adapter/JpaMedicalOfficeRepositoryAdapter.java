package com.mydoctor.infrastructure.adapter;

import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.infrastructure.repository.MedicalOfficeRepository;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.application.command.MedicalOfficeSearchCriteriaCommand;
import com.mydoctor.infrastructure.repository.specification.MedicalOfficeSpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Override
    public List<MedicalOfficeEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<MedicalOfficeEntity> get(MedicalOfficeSearchCriteriaCommand criteria) {
        Specification<MedicalOfficeEntity> spec = new MedicalOfficeSpecificationBuilder().with(criteria).build();
        return repository.findAll(spec);
    }

    @Override
    public boolean existById(Long medicalOfficeId) {
        return repository.existsById(medicalOfficeId);
    }
}
