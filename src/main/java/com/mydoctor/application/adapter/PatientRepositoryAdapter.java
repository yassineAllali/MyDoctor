package com.mydoctor.application.adapter;

import com.mydoctor.application.resource.PatientResource;

public interface PatientRepositoryAdapter {

    PatientResource save(PatientResource patientResource);
}
