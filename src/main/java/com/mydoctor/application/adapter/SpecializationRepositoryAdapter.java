package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.SpecializationEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SpecializationRepositoryAdapter {

    SpecializationEntity save(SpecializationEntity entity);
    Optional<SpecializationEntity> get(long id);
    List<SpecializationEntity> getAll();
    void delete(long id);
    boolean existById(long id);
    Set<SpecializationEntity> get(Set<Long> ids);
}
