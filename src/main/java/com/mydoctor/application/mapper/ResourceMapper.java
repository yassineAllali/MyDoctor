package com.mydoctor.application.mapper;

import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.domaine.medical.MedicalOffice;

public class ResourceMapper {

    public MedicalOffice map(MedicalOfficeResource resource) {
        return new MedicalOffice(resource.name(), null, null, null, null);
    }
}
