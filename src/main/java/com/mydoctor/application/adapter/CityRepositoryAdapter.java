package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.CityEntity;

import java.util.List;
import java.util.Optional;

public interface CityRepositoryAdapter {

    Optional<CityEntity> get(Long id);
    List<CityEntity> getAll();
    CityEntity save(CityEntity entity);
    void delete(Long id);
    boolean existById(long id);
}
