package com.mydoctor.application.mapper;

import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.application.resource.PatientResource;
import com.mydoctor.application.resource.WorkingIntervalResource;
import com.mydoctor.domaine.appointment.booking.TimeSlot;
import com.mydoctor.domaine.appointment.booking.WorkingTimeInterval;
import com.mydoctor.domaine.medical.MedicalOffice;
import com.mydoctor.infrastructure.entity.AppointmentEntity;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.entity.PatientEntity;

import java.util.List;

public class ResourceMapper {

    public AppointmentResource map(AppointmentEntity entity) {
        if(entity == null)
            return null;
        return new AppointmentResource(entity.getId(), map(entity.getPatient()), map(entity.getMedicalOffice()), entity.getDate(), entity.getStart(), entity.getEnd(), entity.getStatus());
    }

    public PatientResource map(PatientEntity entity) {
        if(entity == null)
            return null;
        return new PatientResource(entity.getId(), entity.getName());
    }

    public MedicalOfficeResource map(MedicalOfficeEntity entity) {
        if(entity == null)
            return null;
        return new MedicalOfficeResource(entity.getId(), entity.getName());
    }
}
