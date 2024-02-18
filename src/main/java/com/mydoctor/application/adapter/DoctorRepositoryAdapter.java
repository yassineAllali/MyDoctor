package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.DoctorEntity;

import java.util.Optional;

public interface DoctorRepositoryAdapter {

    Optional<DoctorEntity> get(Long id);
}
