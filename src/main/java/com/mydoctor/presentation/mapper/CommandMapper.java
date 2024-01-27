package com.mydoctor.presentation.mapper;

import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.presentation.request.CreateAppointmentRequest;
import com.mydoctor.presentation.request.CreatePatientRequest;

public class CommandMapper {

    public CreateAppointmentCommand map(CreateAppointmentRequest request) {
        return new CreateAppointmentCommand(request.date(), request.start(), request.end());
    }

    public CreatePatientCommand map(CreatePatientRequest request) {
        return new CreatePatientCommand(request.name());
    }
}
