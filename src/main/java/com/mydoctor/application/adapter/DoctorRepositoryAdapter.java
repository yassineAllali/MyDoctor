package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.DoctorEntity;

import java.util.List;
import java.util.Optional;

public interface DoctorRepositoryAdapter {

    Optional<DoctorEntity> get(Long id);
    List<DoctorEntity> getAll();
    DoctorEntity save(DoctorEntity entity);
    void delete(Long id);
    boolean existById(long id);
}
