package com.mydoctor.presentation.request;

import com.mydoctor.presentation.request.create.CreateAppointmentRequest;
import com.mydoctor.presentation.request.create.CreatePatientRequest;

public record ScheduleRequest(CreateAppointmentRequest appointment, CreatePatientRequest patient) {

}
