package com.mydoctor.presentation.request;

public record MedicalOfficeSearchCriteriaRequest(String name, long cityId, long specializationId) {
}
