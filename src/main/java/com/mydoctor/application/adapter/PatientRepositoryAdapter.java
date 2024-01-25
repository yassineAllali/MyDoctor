package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.PatientEntity;

public interface PatientRepositoryAdapter {

    PatientEntity save(PatientEntity patientEntity);
}
