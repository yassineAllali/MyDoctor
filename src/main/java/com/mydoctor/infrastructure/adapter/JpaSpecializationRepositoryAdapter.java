package com.mydoctor.infrastructure.adapter;

import com.mydoctor.application.adapter.SpecializationRepositoryAdapter;
import com.mydoctor.infrastructure.entity.SpecializationEntity;
import com.mydoctor.infrastructure.repository.SpecializationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class JpaSpecializationRepositoryAdapter implements SpecializationRepositoryAdapter {

    private final SpecializationRepository repository;

    public JpaSpecializationRepositoryAdapter(SpecializationRepository repository) {
        this.repository = repository;
    }

    @Override
    public SpecializationEntity save(SpecializationEntity entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<SpecializationEntity> get(long id) {
        return repository.findById(id);
    }

    @Override
    public List<SpecializationEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existById(long id) {
        return repository.existsById(id);
    }

    @Override
    public Set<SpecializationEntity> get(Set<Long> ids) {
        return repository.findByIdIn(ids);
    }
}
