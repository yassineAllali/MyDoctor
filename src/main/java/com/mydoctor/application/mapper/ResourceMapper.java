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
        return AppointmentResource.builder()
                .id(entity.getId())
                .doctor(map(entity.getDoctor()))
                .patient(map(entity.getPatient()))
                .medicalOffice(map(entity.getMedicalOffice()))
                .date(entity.getDate())
                .start(entity.getStart())
                .end(entity.getEnd())
                .status(entity.getStatus())
                .workingInterval(mapWithoutAppointment(entity.getWorkingInterval()))
                .build();
    }

    private WorkingIntervalResource mapWithoutAppointment(WorkingIntervalEntity entity) {
        if(entity == null)
            return null;
        return WorkingIntervalResource.builder()
                .id(entity.getId())
                .start(entity.getStart())
                .end(entity.getEnd())
                .date(entity.getDate())
                .doctor(map(entity.getDoctor()))
                .medicalOffice(map(entity.getMedicalOffice()))
                .build();
    }

    public PatientResource map(PatientEntity entity) {
        if(entity == null)
            return null;
        return new PatientResource(entity.getId(), entity.getName());
    }

    public MedicalOfficeResource map(MedicalOfficeEntity entity) {
        if(entity == null)
            return null;

        return MedicalOfficeResource.builder()
                .id(entity.getId())
                .city(map(entity.getCity()))
                .name(entity.getName())
                .specializations(entity.getSpecializations() == null ? Set.of() :
                        entity.getSpecializations()
                                .stream()
                                .map(this::map)
                                .collect(Collectors.toSet()))
                .doctors(entity.getDoctors() == null ? Set.of() :
                        entity.getDoctors()
                                .stream()
                                .map(this::map)
                                .collect(Collectors.toSet()))
                .build();

    }

    // TODO : add non cyclic mapper
    public DoctorResource map(DoctorEntity entity) {
        if(entity == null)
            return null;
        return new DoctorResource(
                entity.getId(),
                entity.getName(),
                map(entity.getSpecializationEntity()), null);
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

    public WorkingIntervalResource map(WorkingIntervalEntity entity) {
        if(entity == null)
            return null;
        return WorkingIntervalResource.builder()
                .id(entity.getId())
                .start(entity.getStart())
                .end(entity.getEnd())
                .date(entity.getDate())
                .doctor(map(entity.getDoctor()))
                .medicalOffice(map(entity.getMedicalOffice()))
                .appointments(entity.getAppointments() == null ? null : entity.getAppointments().stream().map(this::mapWithoutWorkingInterval).toList())
                .build();
    }

    private AppointmentResource mapWithoutWorkingInterval(AppointmentEntity entity) {
        if(entity == null)
            return null;
        return AppointmentResource.builder()
                .id(entity.getId())
                .doctor(map(entity.getDoctor()))
                .patient(map(entity.getPatient()))
                .medicalOffice(map(entity.getMedicalOffice()))
                .date(entity.getDate())
                .start(entity.getStart())
                .end(entity.getEnd())
                .status(entity.getStatus())
                .build();
    }
}
