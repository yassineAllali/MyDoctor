package com.mydoctor.presentation.mapper;

import com.mydoctor.application.resource.*;
import com.mydoctor.presentation.response.*;

import java.util.Set;
import java.util.stream.Collectors;

public class ResponseMapper {

    public AppointmentResponse map(AppointmentResource resource) {
        return new AppointmentResponse(resource.id(), resource.date(), resource.start(), resource.end(),
                resource.medicalOffice().id(), resource.doctor().id(), resource.patient().id(), resource.status());
    }

    public TimeSlotResponse map(TimeSlotResource resource) {
        return new TimeSlotResponse(resource.start(), resource.end());
    }

    public MedicalOfficeResponse map(MedicalOfficeResource resource) {

        return MedicalOfficeResponse.builder()
                .id(resource.id())
                .name(resource.name())
                .city(map(resource.city()))
                .specializations(resource.specializations() == null ? Set.of() :
                        resource.specializations()
                                .stream()
                                .map(this::map)
                                .collect(Collectors.toSet()))
                .doctors(resource.doctors() == null ? Set.of() :
                        resource.doctors()
                                .stream()
                                .map(this::map)
                                .collect(Collectors.toSet()))
                .build();
    }

    public CityResponse map(CityResource resource) {
        return new CityResponse(resource.id(), resource.name());
    }

    public SpecializationResponse map(SpecializationResource resource) {
        return new SpecializationResponse(resource.id(), resource.name());
    }

    public DoctorResponse map(DoctorResource resource) {
        return DoctorResponse.builder()
                .id(resource.id())
                .name(resource.name())
                .specialization(map(resource.specialization()))
                .build();
    }

    public PatientResponse map(PatientResource resource) {
        return PatientResponse.builder()
                .id(resource.id())
                .name(resource.name())
                .build();
    }

    public WorkingIntervalResponse map(WorkingIntervalResource resource) {
        return WorkingIntervalResponse.builder()
                .id(resource.id())
                .date(resource.date())
                .start(resource.start())
                .end(resource.end())
                .medicalOffice(map(resource.medicalOffice()))
                .doctor(map(resource.doctor()))
                .appointments(resource.appointments() == null ? null :
                        resource.appointments()
                        .stream()
                        .map(this::map)
                        .toList())
                .build();
    }
}
