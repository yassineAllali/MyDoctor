package com.mydoctor.presentation.request.create;

import lombok.Builder;

@Builder
public record CreatePatientRequest(String name) {
}
