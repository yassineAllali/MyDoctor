package com.mydoctor.presentation.response;

import lombok.Builder;

@Builder
public record DoctorResponse(Long id, String name, SpecializationResponse specialization) {
}

