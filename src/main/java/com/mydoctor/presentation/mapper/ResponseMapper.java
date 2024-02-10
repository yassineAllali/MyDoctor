package com.mydoctor.presentation.mapper;

import com.mydoctor.application.resource.*;
import com.mydoctor.presentation.response.*;

import java.util.stream.Collectors;

public class ResponseMapper {

    public AppointmentResponse map(AppointmentResource resource) {
        return new AppointmentResponse(resource.id(), resource.date(), resource.start(), resource.end(),
                resource.medicalOffice().id(), resource.patient().id(), resource.status());
    }

    public TimeSlotResponse map(TimeSlotResource resource) {
        return new TimeSlotResponse(resource.start(), resource.end());
    }

    public MedicalOfficeResponse map(MedicalOfficeResource resource) {
        return new MedicalOfficeResponse(
                resource.id(),
                resource.name(),
                map(resource.city()),
                resource.specializations()
                        .stream()
                        .map(this::map)
                        .collect(Collectors.toSet())
        );
    }

    public CityResponse map(CityResource resource) {
        return new CityResponse(resource.id(), resource.name());
    }

    public SpecializationResponse map(SpecializationResource resource) {
        return new SpecializationResponse(resource.id(), resource.name());
    }
}
