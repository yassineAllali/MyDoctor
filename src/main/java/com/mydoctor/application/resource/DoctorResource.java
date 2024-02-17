package com.mydoctor.application.resource;

public record DoctorResource(long id, String name, SpecializationResource specialization) {
}
