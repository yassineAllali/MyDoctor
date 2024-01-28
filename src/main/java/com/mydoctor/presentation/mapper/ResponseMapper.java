package com.mydoctor.presentation.mapper;

import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.presentation.response.AppointmentResponse;

public class ResponseMapper {

    public AppointmentResponse map(AppointmentResource resource) {
        return new AppointmentResponse(resource.id(), resource.date(), resource.start(), resource.end(),
                resource.medicalOffice().id(), resource.patient().id());
    }
}
