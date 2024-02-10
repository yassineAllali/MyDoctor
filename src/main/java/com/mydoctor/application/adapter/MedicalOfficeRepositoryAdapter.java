package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.application.command.MedicalOfficeSearchCriteriaCommand;

import java.util.List;
import java.util.Optional;

public interface MedicalOfficeRepositoryAdapter {

    MedicalOfficeEntity save(MedicalOfficeEntity medicalOfficeEntity);

    Optional<MedicalOfficeEntity> get(Long medicalOfficeId);

    List<MedicalOfficeEntity> get(MedicalOfficeSearchCriteriaCommand criteria);
}
