package com.mydoctor.application.service;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.mapper.DomainMapper;
import com.mydoctor.application.mapper.EntityMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.domaine.appointment.Appointment;
import com.mydoctor.infrastructure.entity.AppointmentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepositoryAdapter appointmentRepository;
    private final ResourceMapper resourceMapper;
    private final DomainMapper domainMapper;
    private final EntityMapper entityMapper;

    public AppointmentService(AppointmentRepositoryAdapter appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
        this.resourceMapper = new ResourceMapper();
        this.domainMapper = new DomainMapper();
        this.entityMapper = new EntityMapper();
    }

    public AppointmentResource getAppointment(long id) {
        AppointmentEntity entity = getAppointmentEntity(id);
        return resourceMapper.map(entity);
    }

    public List<AppointmentResource> getPatientAppointments(long patientId) {
        return appointmentRepository.getPatientAppointments(patientId)
                .stream()
                .map(resourceMapper::map)
                .toList();
    }

    public AppointmentResource cancelAppointment(long id) {
        AppointmentEntity entity = getAppointmentEntity(id);
        return resourceMapper.map(cancelAppointment(entity));
    }

    private AppointmentEntity cancelAppointment(AppointmentEntity entity) {
        Appointment appointment = domainMapper.map(entity);
        appointment.canceled();
        entity.setStatus(appointment.getStatus().name());
        return entity;
    }

    private AppointmentEntity getAppointmentEntity(long id) {
        return appointmentRepository
                .get(id)
                .orElseThrow(() -> new NotFoundException(String.format("Appointment with id : %s not found !", id)));
    }
}
