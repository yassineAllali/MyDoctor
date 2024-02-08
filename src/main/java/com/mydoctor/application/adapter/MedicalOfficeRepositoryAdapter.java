package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.repository.criteria.MedicalOfficeSearchCriteria;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MedicalOfficeRepositoryAdapter {

    MedicalOfficeEntity save(MedicalOfficeEntity medicalOfficeEntity);

    Optional<MedicalOfficeEntity> get(Long medicalOfficeId);

    List<MedicalOfficeEntity> get(MedicalOfficeSearchCriteria criteria);
}
