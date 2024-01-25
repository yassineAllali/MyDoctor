package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;

import java.util.Optional;

public interface MedicalOfficeRepositoryAdapter {

    MedicalOfficeEntity save(MedicalOfficeEntity medicalOfficeEntity);

    Optional<MedicalOfficeEntity> get(Long medicalOfficeId);
}
