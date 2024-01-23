package com.mydoctor.application.service;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.adapter.PatientRepositoryAdapter;
import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.mapper.CommandMapper;
import com.mydoctor.application.mapper.DomainMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.application.resource.PatientResource;
import com.mydoctor.application.resource.WorkingIntervalResource;
import com.mydoctor.domaine.appointment.Appointment;
import com.mydoctor.domaine.appointment.booking.BookablePeriod;
import com.mydoctor.domaine.appointment.booking.BookingException;
import com.mydoctor.domaine.appointment.booking.WorkingDay;
import com.mydoctor.domaine.appointment.booking.WorkingTimeInterval;
import com.mydoctor.domaine.medical.MedicalOffice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SchedulingService {

    private final AppointmentRepositoryAdapter appointmentRepository;
    private final WorkingIntervalRepositoryAdapter workingIntervalRepository;
    private final PatientRepositoryAdapter patientRepository;
    private final MedicalOfficeRepositoryAdapter medicalOfficeRepository;
    private final CommandMapper commandMapper;
    private final ResourceMapper resourceMapper;
    private final DomainMapper domainMapper;

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
        domainMapper = new DomainMapper();
    }

    public AppointmentResource schedule(CreateAppointmentCommand appointmentCommand,
                                        CreatePatientCommand patientCommand, Long medicalOfficeId) {

        WorkingDay workingDay = getWorkingDay(medicalOfficeId, appointmentCommand.date());
        Appointment appointment = commandMapper.map(appointmentCommand);
        book(workingDay, appointment);

        return saveAppointment(appointment, patientCommand, medicalOfficeId);
    }

    private AppointmentResource saveAppointment(Appointment appointment, CreatePatientCommand patientCommand, Long medicalOfficeId) {
        PatientResource patientResource = patientRepository.save(commandMapper.map(patientCommand));
        MedicalOfficeResource medicalOfficeResource = medicalOfficeRepository
                .get(medicalOfficeId)
                .orElseThrow(() -> new NotFoundException("Medical office not found for id : " + medicalOfficeId));
        return appointmentRepository
                .save(new AppointmentResource(null, patientResource,
                        medicalOfficeResource, appointment.getDate(),
                        appointment.getStart(), appointment.getEnd()));
    }


    private WorkingDay getWorkingDay(Long medicalOfficeId, LocalDate date) {
        List<WorkingIntervalResource> workingIntervalResources = workingIntervalRepository.get(medicalOfficeId, date);

        return generateWorkingDay(workingIntervalResources)
                .orElseThrow(() -> new BookingException(
                        String.format("Can't book appointment, %s is not a working day !", date)));
    }

    private void book(WorkingDay workingDay, Appointment appointment) {
        try {
            workingDay.book(appointment.getTimeSlot());
        } catch (BookingException e) {
            throw new BookingException("Can't book appointment : " + e.getMessage());
        }
    }

    private Optional<WorkingDay> generateWorkingDay(List<WorkingIntervalResource> workingIntervals) {
        if(workingIntervals.isEmpty())
            return Optional.empty();
        LocalDate date = workingIntervals.get(0).date();
        List<WorkingTimeInterval> workingTimeIntervals = workingIntervals.stream().map(resourceMapper::map).toList();
        return Optional.of(new WorkingDay(date, workingTimeIntervals));
    }

    private MedicalOffice getMedicalOffice(Long medicalOfficeId) {
        MedicalOfficeResource medicalOfficeResource = medicalOfficeRepository.get(medicalOfficeId).orElseThrow(() -> new RuntimeException("not found"));
        return resourceMapper.map(medicalOfficeResource);
    }
}
