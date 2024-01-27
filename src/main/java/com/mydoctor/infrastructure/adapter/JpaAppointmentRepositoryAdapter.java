package com.mydoctor.infrastructure.adapter;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.infrastructure.entity.AppointmentEntity;
import com.mydoctor.infrastructure.repository.AppointmentRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

//@Repository
public class JpaAppointmentRepositoryAdapter implements AppointmentRepositoryAdapter {

    private final AppointmentRepository repository;

    public JpaAppointmentRepositoryAdapter(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public AppointmentEntity save(AppointmentEntity appointmentEntity) {
        return repository.save(appointmentEntity);
    }
}
