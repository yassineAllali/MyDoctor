package com.mydoctor.application.service;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.resource.AppointmentResource;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final AppointmentRepositoryAdapter appointmentRepository;

    public AppointmentService(AppointmentRepositoryAdapter appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


}
