package com.mydoctor.presentation.response;

import lombok.Builder;

@Builder
public record PatientResponse(Long id, String name) {
}
