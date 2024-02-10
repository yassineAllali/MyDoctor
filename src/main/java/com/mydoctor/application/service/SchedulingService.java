package com.mydoctor.application.service;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.adapter.PatientRepositoryAdapter;
import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.application.command.CreateAppointmentCommand;
import com.mydoctor.application.command.CreatePatientCommand;
import com.mydoctor.application.exception.BusinessException;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.mapper.DomainMapper;
import com.mydoctor.application.mapper.EntityMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.domaine.appointment.Appointment;
import com.mydoctor.domaine.appointment.booking.BookingException;
import com.mydoctor.domaine.appointment.booking.WorkingTimeInterval;
import com.mydoctor.infrastructure.entity.AppointmentEntity;
import com.mydoctor.infrastructure.entity.PatientEntity;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SchedulingService {

    private final AppointmentRepositoryAdapter appointmentRepository;
    private final WorkingIntervalRepositoryAdapter workingIntervalRepository;
    private final PatientRepositoryAdapter patientRepository;
    private final MedicalOfficeRepositoryAdapter medicalOfficeRepository;
    private final ResourceMapper resourceMapper;
    private final DomainMapper domainMapper;
    private final EntityMapper entityMapper;

    public SchedulingService(AppointmentRepositoryAdapter appointmentRepository,
                             WorkingIntervalRepositoryAdapter workingIntervalRepository,
                             PatientRepositoryAdapter patientRepository,
                             MedicalOfficeRepositoryAdapter medicalOfficeRepository) {
        this.appointmentRepository = appointmentRepository;
        this.workingIntervalRepository = workingIntervalRepository;
        this.patientRepository = patientRepository;
        this.medicalOfficeRepository = medicalOfficeRepository;
        this.resourceMapper = new ResourceMapper();
        this.domainMapper = new DomainMapper();
        this.entityMapper = new EntityMapper();
    }

    public AppointmentResource schedule(CreateAppointmentCommand appointmentCommand,
                                        CreatePatientCommand patientCommand, long medicalOfficeId) {
        log.info("Scheduling an appointment for new patient at the medical Office {}", medicalOfficeId);
        PatientEntity patientEntity = patientRepository.save(entityMapper.map(patientCommand));
        return schedule(appointmentCommand, medicalOfficeId, patientEntity);
    }

    public AppointmentResource schedule(CreateAppointmentCommand appointmentCommand, long medicalOfficeId, long patientId) {
        log.info("Scheduling an appointment for patient {} at the medical Office {}", patientId, medicalOfficeId);
        PatientEntity patientEntity = getPatientEntity(patientId);
        return schedule(appointmentCommand, medicalOfficeId, patientEntity);
    }

    private PatientEntity getPatientEntity(long id) {
        return patientRepository.get(id)
                .orElseThrow(() -> {
                    log.info("Patient with id {} not found !", id);
                    throw new NotFoundException(String.format("Patient with id %s not found !", id));
                });
    }

    private AppointmentResource schedule(CreateAppointmentCommand appointmentCommand, long medicalOfficeId, PatientEntity patientEntity) {
        WorkingIntervalEntity workingIntervalEntity = getWhereAppointmentInside(medicalOfficeId, appointmentCommand);
        Appointment appointment = domainMapper.map(appointmentCommand);
        WorkingTimeInterval workingInterval = domainMapper.map(workingIntervalEntity);
        try {
            workingInterval.book(appointment.getTimeSlot());
            appointment.booked();
        } catch (BookingException e) {
            log.error("Booking exception : " + e.getMessage());
            throw new BusinessException("Can't book appointment !", e.getCause());
        }
        return resourceMapper.map(appointmentRepository.save(new AppointmentEntity(null, patientEntity,
                workingIntervalEntity.getMedicalOffice(), workingIntervalEntity, appointment.getDate(),
                appointment.getStart(), appointment.getEnd(), appointment.getStatus().name())));
    }

    private WorkingIntervalEntity getWhereAppointmentInside(Long medicalOfficeId, CreateAppointmentCommand appointmentCommand) {
        log.info("Getting inside which WorkingInterval of the medical office {} the appointment for the day {} between {} {}", medicalOfficeId, appointmentCommand.date(), appointmentCommand.start(), appointmentCommand.end());
        List<WorkingIntervalEntity> entities = workingIntervalRepository.get(medicalOfficeId, appointmentCommand.date(), appointmentCommand.start(), appointmentCommand.end());
        if(entities.size() == 1)
            return entities.get(0);
        else if(entities.size() > 1) {
            log.error("Inconsistent data : founded {} WorkingIntervals where an appointment can be inside", entities.size());
            throw new BusinessException("Can't book appointment !");
        }
        else {
            log.error("Appointment is not inside any working interval !");
            throw new BusinessException("Can't book appointment !");
        }
    }
}
