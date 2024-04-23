package com.mydoctor.application.resource;

import lombok.Builder;

@Builder
public record SpecializationResource(Long id, String name) {
}
