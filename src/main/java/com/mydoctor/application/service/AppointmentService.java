package com.mydoctor.application.service;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.AppointmentResource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepositoryAdapter appointmentRepository;
    private final ResourceMapper resourceMapper;

    public AppointmentService(AppointmentRepositoryAdapter appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
        this.resourceMapper = new ResourceMapper();
    }

    public List<AppointmentResource> getPatientAppointments(long patientId) {
        return appointmentRepository.getPatientAppointments(patientId)
                .stream()
                .map(resourceMapper::map)
                .toList();
    }


}
