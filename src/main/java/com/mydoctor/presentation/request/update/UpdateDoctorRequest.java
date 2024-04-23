package com.mydoctor.presentation.request.update;

public record UpdateDoctorRequest(Long id, String name, Long specializationId) {
}
