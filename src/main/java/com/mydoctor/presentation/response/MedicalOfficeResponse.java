package com.mydoctor.presentation.response;

import lombok.Builder;

import java.util.Set;

@Builder
public record MedicalOfficeResponse(long id, String name, CityResponse city, Set<SpecializationResponse> specializations, Set<DoctorResponse> doctors) {
}
