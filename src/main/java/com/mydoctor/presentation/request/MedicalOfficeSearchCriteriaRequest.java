package com.mydoctor.presentation.request;

public record MedicalOfficeSearchCriteriaRequest(String name, Long cityId, Long specializationId) {
}
