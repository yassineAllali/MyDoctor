package com.mydoctor.application.mapper;

import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.application.resource.WorkingIntervalResource;
import com.mydoctor.domaine.appointment.booking.TimeSlot;
import com.mydoctor.domaine.appointment.booking.WorkingTimeInterval;
import com.mydoctor.domaine.medical.MedicalOffice;

import java.util.List;

public class ResourceMapper {

    public MedicalOffice map(MedicalOfficeResource resource) {
        return new MedicalOffice(resource.name(), null, null, null, null);
    }

    public WorkingTimeInterval map(WorkingIntervalResource resource) {
        return new WorkingTimeInterval(resource.start(), resource.end(),
                resource.appointments() != null ? resource.appointments().stream().map(a -> new TimeSlot(a.start(), a.end())).toList() : List.of());
    }
}
