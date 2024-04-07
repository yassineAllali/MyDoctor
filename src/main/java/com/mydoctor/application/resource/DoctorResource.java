package com.mydoctor.application.resource;

import lombok.Builder;

@Builder
public record DoctorResource(Long id, String name, SpecializationResource specialization) {
}
