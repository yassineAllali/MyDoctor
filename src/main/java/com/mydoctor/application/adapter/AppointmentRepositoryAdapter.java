package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.AppointmentEntity;

import java.util.List;

public interface AppointmentRepositoryAdapter {

    AppointmentEntity save(AppointmentEntity appointmentEntity);
    List<AppointmentEntity> getPatientAppointments(long patient);
}
