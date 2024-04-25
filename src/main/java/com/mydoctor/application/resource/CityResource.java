package com.mydoctor.application.resource;

import lombok.Builder;

@Builder
public record CityResource(Long id, String name) {
}
