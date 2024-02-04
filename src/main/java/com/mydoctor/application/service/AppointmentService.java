package com.mydoctor.application.service;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.mapper.DomainMapper;
import com.mydoctor.application.mapper.EntityMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.application.resource.TimeSlotResource;
import com.mydoctor.domaine.appointment.Appointment;
import com.mydoctor.domaine.appointment.booking.TimeSlot;
import com.mydoctor.domaine.appointment.booking.WorkingDay;
import com.mydoctor.domaine.appointment.booking.WorkingTimeInterval;
import com.mydoctor.infrastructure.entity.AppointmentEntity;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepositoryAdapter appointmentRepository;
    private final WorkingIntervalRepositoryAdapter workingIntervalRepository;
    private final ResourceMapper resourceMapper;
    private final DomainMapper domainMapper;
    private final EntityMapper entityMapper;

    public AppointmentService(AppointmentRepositoryAdapter appointmentRepository, WorkingIntervalRepositoryAdapter workingIntervalRepository) {
        this.appointmentRepository = appointmentRepository;
        this.workingIntervalRepository = workingIntervalRepository;
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

    public List<TimeSlotResource> getAvailableSlots(long medOfficeId, LocalDate date, Duration duration) {
        List<WorkingTimeInterval> workingIntervals = getWorkingIntervals(medOfficeId, date);
        WorkingDay workingDay = new WorkingDay(date, workingIntervals);
        List<TimeSlot> availableTimeSlots = workingDay.getAvailableSlots(duration);
        return availableTimeSlots.stream().map(s -> resourceMapper.map(s)).toList();
    }

    private AppointmentEntity getAppointmentEntity(long id) {
        return appointmentRepository
                .get(id)
                .orElseThrow(() -> new NotFoundException(String.format("Appointment with id : %s not found !", id)));
    }

    private List<WorkingTimeInterval> getWorkingIntervals(long medOfficeId, LocalDate date) {
        List<WorkingIntervalEntity> entities = workingIntervalRepository.get(medOfficeId, date);
        return entities.stream().map(e -> domainMapper.map(e)).toList();
    }
}
