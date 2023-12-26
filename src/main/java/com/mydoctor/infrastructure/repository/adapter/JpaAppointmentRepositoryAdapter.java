package com.mydoctor.infrastructure.repository.adapter;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.infrastructure.repository.AppointmentRepository;
import com.mydoctor.infrastructure.repository.entity.AppointmentEntity;
import com.mydoctor.infrastructure.repository.mapper.ApplicationMapper;
import org.springframework.stereotype.Service;

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
