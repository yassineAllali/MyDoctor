package com.mydoctor.application.resource;

import lombok.Builder;

import java.util.Set;

@Builder
public record MedicalOfficeResource(Long id, String name, CityResource city, Set<SpecializationResource> specializations, Set<DoctorResource> doctors) {
}
