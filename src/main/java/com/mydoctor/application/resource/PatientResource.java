package com.mydoctor.application.resource;

import lombok.Builder;

@Builder
public record PatientResource(Long id, String name) {
}
