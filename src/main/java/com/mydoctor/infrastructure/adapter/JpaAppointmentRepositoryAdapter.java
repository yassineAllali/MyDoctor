package com.mydoctor.infrastructure.adapter;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.domaine.appointment.booking.BookablePeriod;
import com.mydoctor.infrastructure.entity.AppointmentEntity;
import com.mydoctor.infrastructure.mapper.ApplicationMapper;
import com.mydoctor.infrastructure.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class JpaAppointmentRepositoryAdapter implements AppointmentRepositoryAdapter {

    private final AppointmentRepository repository;
    private final ApplicationMapper mapper;

    public JpaAppointmentRepositoryAdapter(AppointmentRepository repository) {
        this.repository = repository;
        mapper = new ApplicationMapper();
    }

    @Override
    public AppointmentResource save(AppointmentResource appointmentResource) {
        AppointmentEntity entity = mapper.map(appointmentResource);
        return mapper.map(repository.save(entity));
    }
}
