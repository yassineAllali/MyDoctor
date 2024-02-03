package com.mydoctor.application.mapper;

import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.application.resource.WorkingIntervalResource;
import com.mydoctor.domaine.appointment.Appointment;
import com.mydoctor.domaine.appointment.booking.TimeSlot;
import com.mydoctor.domaine.appointment.booking.WorkingTimeInterval;
import com.mydoctor.domaine.medical.MedicalOffice;
import com.mydoctor.infrastructure.entity.AppointmentEntity;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;

import java.util.List;

public class DomainMapper {

    public WorkingTimeInterval map(WorkingIntervalEntity entity) {
        return new WorkingTimeInterval(entity.getStart(),
                entity.getEnd(),
                entity.getAppointments() != null ? entity.getAppointments().stream().map(this::getTimeSlot).toList() : List.of());
    }

    public TimeSlot getTimeSlot(AppointmentEntity appointmentEntity) {
        return new TimeSlot(appointmentEntity.getStart(), appointmentEntity.getEnd());
    }

    public Appointment map(AppointmentEntity entity) {
        if(entity == null)
            return null;
        return new Appointment(entity.getDate(), getTimeSlot(entity), Appointment.Status.valueOf(entity.getStatus()));
    }

    public Appointment map(CreateAppointmentCommand createAppointmentCommand) {
        TimeSlot timeSlot = new TimeSlot(createAppointmentCommand.start(), createAppointmentCommand.end());
        return new Appointment(createAppointmentCommand.date(), timeSlot);
    }

    public MedicalOffice map(MedicalOfficeEntity entity) {
        return new MedicalOffice(entity.getName(), null, null, null, null);
    }

    public MedicalOffice map(MedicalOfficeResource resource) {
        return new MedicalOffice(resource.name(), null, null, null, null);
    }

    public WorkingTimeInterval map(WorkingIntervalResource resource) {
        return new WorkingTimeInterval(resource.start(), resource.end(),
                resource.appointments() != null ? resource.appointments().stream().map(a -> new TimeSlot(a.start(), a.end())).toList() : List.of());
    }
}
