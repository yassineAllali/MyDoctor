package com.mydoctor.application.mapper;

import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.domaine.appointment.Appointment;

public class DomainMapper {

    public AppointmentResource map(Appointment appointment) {
        return new AppointmentResource(null, null, null, appointment.getDate(), appointment.getStart(), appointment.getEnd());
    }
}
