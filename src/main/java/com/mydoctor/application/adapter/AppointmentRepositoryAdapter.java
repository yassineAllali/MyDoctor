package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.AppointmentEntity;

public interface AppointmentRepositoryAdapter {

    AppointmentEntity save(AppointmentEntity appointmentEntity);
}
