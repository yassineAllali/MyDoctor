package com.mydoctor.infrastructure.adapter;

import com.mydoctor.application.adapter.DoctorRepositoryAdapter;
import com.mydoctor.infrastructure.entity.DoctorEntity;
import com.mydoctor.infrastructure.repository.DoctorRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaDoctorRepositoryAdapter implements DoctorRepositoryAdapter {

    private final DoctorRepository repository;

    public JpaDoctorRepositoryAdapter(DoctorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<DoctorEntity> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<DoctorEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public DoctorEntity save(DoctorEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existById(long id) {
        return repository.existsById(id);
    }
}
