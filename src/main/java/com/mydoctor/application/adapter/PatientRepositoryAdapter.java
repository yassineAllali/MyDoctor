package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.PatientEntity;

import java.util.List;
import java.util.Optional;

public interface PatientRepositoryAdapter {

    PatientEntity save(PatientEntity patientEntity);
    Optional<PatientEntity> get(long id);
    List<PatientEntity> getAll();
    void delete(long id);
    boolean existById(long id);
}
