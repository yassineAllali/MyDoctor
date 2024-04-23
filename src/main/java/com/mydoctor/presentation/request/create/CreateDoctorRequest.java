package com.mydoctor.presentation.request.create;

import lombok.Builder;

@Builder
public record CreateDoctorRequest(String name, Long specializationId) {
}
