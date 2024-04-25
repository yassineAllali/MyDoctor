package com.mydoctor.presentation.request.create;

import lombok.Builder;

import java.util.Set;

@Builder
public record CreateMedicalOfficeRequest(String name, Long cityId, Set<Long> specializationIds) {
}
