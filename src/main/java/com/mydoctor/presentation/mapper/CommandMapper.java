package com.mydoctor.presentation.mapper;

import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.command.MedicalOfficeSearchCriteriaCommand;
import com.mydoctor.presentation.request.CreateAppointmentRequest;
import com.mydoctor.presentation.request.CreatePatientRequest;
import com.mydoctor.presentation.request.MedicalOfficeSearchCriteriaRequest;

public class CommandMapper {

    public CreateAppointmentCommand map(CreateAppointmentRequest request) {
        return new CreateAppointmentCommand(request.date(), request.start(), request.end());
    }

    public CreatePatientCommand map(CreatePatientRequest request) {
        return new CreatePatientCommand(request.name());
    }

    public MedicalOfficeSearchCriteriaCommand map(MedicalOfficeSearchCriteriaRequest request) {
        return new MedicalOfficeSearchCriteriaCommand(request.name(), request.cityId(), request.specializationId());
    }
}
