package com.mydoctor.presentation.mapper;

import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.application.resource.TimeSlotResource;
import com.mydoctor.presentation.response.AppointmentResponse;
import com.mydoctor.presentation.response.TimeSlotResponse;

public class ResponseMapper {

    public AppointmentResponse map(AppointmentResource resource) {
        return new AppointmentResponse(resource.id(), resource.date(), resource.start(), resource.end(),
                resource.medicalOffice().id(), resource.patient().id(), resource.status());
    }

    public TimeSlotResponse map(TimeSlotResource resource) {
        return new TimeSlotResponse(resource.start(), resource.end());
    }
}
