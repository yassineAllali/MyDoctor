package com.mydoctor.application.resource;

import java.util.Set;

public record MedicalOfficeResource(Long id, String name, CityResource city, Set<SpecializationResource> specializations) {
}
