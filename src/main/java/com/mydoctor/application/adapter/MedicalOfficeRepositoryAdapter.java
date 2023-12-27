package com.mydoctor.application.adapter;

import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.domaine.medical.MedicalOffice;

import java.util.Optional;

public interface MedicalOfficeRepositoryAdapter {

    MedicalOfficeResource save(MedicalOfficeResource medicalOfficeResource);

    Optional<MedicalOfficeResource> get(Long medicalOfficeId);
}
