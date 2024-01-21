package com.mydoctor.application.service;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.adapter.PatientRepositoryAdapter;
import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.mapper.CommandMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.application.resource.WorkingIntervalResource;
import com.mydoctor.domaine.appointment.Appointment;
import com.mydoctor.domaine.appointment.booking.BookablePeriod;
import com.mydoctor.domaine.appointment.booking.BookingException;
import com.mydoctor.domaine.medical.MedicalOffice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulingService {

    private final AppointmentRepositoryAdapter appointmentRepository;
    private final WorkingIntervalRepositoryAdapter workingIntervalRepository;
    private final PatientRepositoryAdapter patientRepository;
    private final MedicalOfficeRepositoryAdapter medicalOfficeRepository;
    private final CommandMapper commandMapper;
    private final ResourceMapper resourceMapper;

    public SchedulingService(AppointmentRepositoryAdapter appointmentRepository,
                             WorkingIntervalRepositoryAdapter workingIntervalRepository,
                             PatientRepositoryAdapter patientRepository,
                             MedicalOfficeRepositoryAdapter medicalOfficeRepository) {
        this.appointmentRepository = appointmentRepository;
        this.workingIntervalRepository = workingIntervalRepository;
        this.patientRepository = patientRepository;
        this.medicalOfficeRepository = medicalOfficeRepository;
        this.commandMapper = new CommandMapper();
        this.resourceMapper = new ResourceMapper();
    }

    public AppointmentResource schedule(CreateAppointmentCommand appointmentCommand,
                                        CreatePatientCommand patientCommand, Long medicalOfficeId) {
        List<WorkingIntervalResource> workingIntervalResources = workingIntervalRepository
                .get(medicalOfficeId, appointmentCommand.date());
        // get intervals with appointments
        // create working day
        // call book on working day
        Appointment appointment = commandMapper.map(appointmentCommand);
        return null;
    }

    private MedicalOffice getMedicalOffice(Long medicalOfficeId) {
        MedicalOfficeResource medicalOfficeResource = medicalOfficeRepository.get(medicalOfficeId).orElseThrow(() -> new RuntimeException("not found"));
        return resourceMapper.map(medicalOfficeResource);
    }
}
