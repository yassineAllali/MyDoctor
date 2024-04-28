package com.mydoctor.application.mapper;

import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.resource.*;
import com.mydoctor.domaine.appointment.Appointment;
import com.mydoctor.infrastructure.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class EntityMapper {

    public PatientEntity map(CreatePatientCommand createPatientCommand) {
        return new PatientEntity(null, createPatientCommand.name());
    }

    public DoctorEntity map(DoctorResource resource) {
        if(resource == null)
            return null;
        return DoctorEntity.builder()
                .id(resource.id())
                .name(resource.name())
                .specializationEntity(map(resource.specialization()))
                .build();
    }

    public SpecializationEntity map(SpecializationResource resource) {
        if(resource == null)
            return null;
        return SpecializationEntity.builder()
                .id(resource.id())
                .name(resource.name())
                .build();
    }

    public PatientEntity map(PatientResource resource) {
        if(resource == null)
            return null;
        return PatientEntity.builder()
                .id(resource.id())
                .name(resource.name())
                .build();
    }

    public WorkingIntervalEntity map(WorkingIntervalResource resource) {
        if(resource == null)
            return null;
        return WorkingIntervalEntity.builder()
                .id(resource.id())
                .medicalOffice(map(resource.medicalOffice()))
                .doctor(map(resource.doctor()))
                .start(resource.start())
                .end(resource.end())
                .date(resource.date())
                .appointments(resource.appointments() == null ? null : resource.appointments().stream().map(this::mapBreakCycle).toList())
                .build();
    }

    private WorkingIntervalEntity mapBreakCycle(WorkingIntervalResource resource) {
        if(resource == null)
            return null;
        return WorkingIntervalEntity.builder()
                .start(resource.start())
                .end(resource.end())
                .date(resource.date())
                .appointments(null)
                .build();
    }

    public AppointmentEntity map(AppointmentResource resource) {
        if(resource == null)
            return null;
        return AppointmentEntity.builder()
                .id(resource.id())
                .start(resource.start())
                .end(resource.end())
                .date(resource.date())
                .medicalOffice(map(resource.medicalOffice()))
                .doctor(map(resource.doctor()))
                .patient(map(resource.patient()))
                .status(resource.status())
                .workingInterval(mapBreakCycle(resource.workingInterval()))
                .build();
    }

    private AppointmentEntity mapBreakCycle(AppointmentResource resource) {
        if(resource == null)
            return null;
        return AppointmentEntity.builder()
                .id(resource.id())
                .start(resource.start())
                .end(resource.end())
                .date(resource.date())
                .medicalOffice(map(resource.medicalOffice()))
                .doctor(map(resource.doctor()))
                .patient(map(resource.patient()))
                .status(resource.status())
                .workingInterval(null)
                .build();
    }

    public MedicalOfficeEntity map(MedicalOfficeResource resource) {
        if(resource == null)
            return null;
        return MedicalOfficeEntity.builder()
                .id(resource.id())
                .city(map(resource.city()))
                .specializations(resource.specializations() == null ? null : resource.specializations().stream().map(this::map).collect(Collectors.toSet()))
                .name(resource.name())
                .doctors(resource.doctors() == null ? null :
                        resource.doctors()
                                .stream()
                                .map(this::map).collect(Collectors.toSet()))
                .build();
    }

    public CityEntity map(CityResource resource) {
        if(resource == null)
            return null;
        return CityEntity.builder()
                .id(resource.id())
                .name(resource.name())
                .build();
    }
}
