package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.AppointmentEntity;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepositoryAdapter {

    AppointmentEntity save(AppointmentEntity appointmentEntity);
    List<AppointmentEntity> getPatientAppointments(long patient);

    Optional<AppointmentEntity> get(long id);
    List<AppointmentEntity> getAll();
    void delete(long id);
    boolean existById(long id);
}
