package com.mydoctor.application.resource;

import lombok.Builder;

import java.util.Set;

@Builder
public record DoctorResource(Long id, String name, SpecializationResource specialization, Set<MedicalOfficeResource> medicalOffices) {
}
