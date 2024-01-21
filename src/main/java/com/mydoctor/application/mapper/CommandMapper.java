package com.mydoctor.application.mapper;

import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.resource.PatientResource;
import com.mydoctor.domaine.appointment.Appointment;
import com.mydoctor.domaine.appointment.booking.TimeSlot;

public class CommandMapper {

    public Appointment map(CreateAppointmentCommand createAppointmentCommand) {
        TimeSlot timeSlot = new TimeSlot(createAppointmentCommand.start(), createAppointmentCommand.end());
        return new Appointment(createAppointmentCommand.date(), timeSlot);
    }

    public PatientResource map(CreatePatientCommand patientCommand) {
        return new PatientResource(null, patientCommand.name());
    }
}
