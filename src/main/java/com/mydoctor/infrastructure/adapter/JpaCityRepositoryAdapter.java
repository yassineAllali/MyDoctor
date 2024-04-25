package com.mydoctor.infrastructure.adapter;

import com.mydoctor.application.adapter.CityRepositoryAdapter;
import com.mydoctor.infrastructure.entity.CityEntity;
import com.mydoctor.infrastructure.repository.CityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCityRepositoryAdapter implements CityRepositoryAdapter {

    private final CityRepository repository;

    public JpaCityRepositoryAdapter(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CityEntity> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<CityEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public CityEntity save(CityEntity entity) {
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
