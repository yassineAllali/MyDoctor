package com.mydoctor.presentation.request.create;

import lombok.Builder;

@Builder
public record CreateCityRequest(String name) {
}
