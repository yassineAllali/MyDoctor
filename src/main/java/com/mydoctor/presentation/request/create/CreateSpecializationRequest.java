package com.mydoctor.presentation.request.create;

import lombok.Builder;

@Builder
public record CreateSpecializationRequest(String name) {
}
