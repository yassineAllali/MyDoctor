package com.mydoctor.presentation.request;

public record ScheduleRequest(CreateAppointmentRequest appointment, CreatePatientRequest patient) {

}
