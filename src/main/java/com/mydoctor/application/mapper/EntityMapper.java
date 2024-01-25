package com.mydoctor.application.mapper;

import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.infrastructure.entity.PatientEntity;

public class EntityMapper {

    public PatientEntity map(CreatePatientCommand createPatientCommand) {
        return new PatientEntity(null, createPatientCommand.name());
    }
}
