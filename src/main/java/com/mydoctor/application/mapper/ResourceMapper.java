package com.mydoctor.application.mapper;

import com.mydoctor.application.resource.*;
import com.mydoctor.domaine.appointment.booking.TimeSlot;
import com.mydoctor.domaine.appointment.booking.WorkingTimeInterval;
import com.mydoctor.domaine.medical.MedicalOffice;
import com.mydoctor.infrastructure.entity.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        return new MedicalOfficeResource(
                entity.getId(),
                entity.getName(),
                map(entity.getCity()),
                entity.getSpecializations() == null ? Set.of() :
                        entity.getSpecializations()
                            .stream()
                            .map(this::map)
                            .collect(Collectors.toSet()));
    }

    public CityResource map(CityEntity entity) {
        if (entity == null)
            return null;
        return new CityResource(entity.getId(), entity.getName());
    }

    public SpecializationResource map(SpecializationEntity entity) {
        if (entity == null)
            return null;
        return new SpecializationResource(entity.getId(), entity.getName());
    }

    public TimeSlotResource map(TimeSlot timeSlot) {
        if (timeSlot == null)
            return null;
        return new TimeSlotResource(timeSlot.getStart(), timeSlot.getEnd());
    }
}
