package com.mydoctor.presentation.response;

import java.util.Set;

public record MedicalOfficeResponse(long id, String name, CityResponse city, Set<SpecializationResponse> specializations) {
}
