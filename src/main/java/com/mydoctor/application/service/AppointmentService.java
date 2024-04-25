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
import com.mydoctor.domaine.appointment.booking.WorkingPeriod;
import com.mydoctor.domaine.appointment.booking.WorkingTimeInterval;
import com.mydoctor.infrastructure.entity.AppointmentEntity;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppointmentService implements ApplicationService<AppointmentResource, Long> {

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

    @Override
    public AppointmentResource get(Long id) throws NotFoundException {
        log.info("Getting appointment with id {} !", id);
        AppointmentEntity entity = appointmentRepository
                .get(id)
                .orElseThrow(() -> new NotFoundException(String.format("Appointment with id : %s not found !", id)));
        return resourceMapper.map(entity);
    }

    @Override
    public List<AppointmentResource> getAll() {
        log.info("Getting all appointments !");
        List<AppointmentEntity> entities = appointmentRepository.getAll();
        return entities.stream()
                .map(resourceMapper::map)
                .toList();
    }

    @Override
    public AppointmentResource create(AppointmentResource resource) throws IllegalArgumentException {
        log.info("Creating new appointment !");
        if(resource.id() != null) {
            throw new IllegalArgumentException("Id should not be set !");
        }
        return save(resource);
    }

    @Override
    public AppointmentResource update(AppointmentResource resource) throws NotFoundException {
        log.info("Updating appointment with id {} !", resource.id());
        checkExists(resource.id());
        return save(resource);
    }

    @Override
    public AppointmentResource save(AppointmentResource resource) {
        log.info("Saving appointment !");
        AppointmentEntity entity = appointmentRepository.save(entityMapper.map(resource));
        return resourceMapper.map(entity);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.info("Deleting appointment with id {} !", id);
        checkExists(id);
        appointmentRepository.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        log.info("Checking if appointment with id {} exist !", id);
        return appointmentRepository.existById(id);
    }

    @Override
    public void checkExists(Long id) throws NotFoundException {
        if(!appointmentRepository.existById(id)) {
            log.info("Appointment with id : {} not found !", id);
            throw new NotFoundException(String.format("Appointment with id : %s not found !", id));
        }
    }

    public List<AppointmentResource> getPatientAppointments(long patientId) {
        log.info("Getting appointments for patient {}", patientId);
        return appointmentRepository.getPatientAppointments(patientId)
                .stream()
                .map(resourceMapper::map)
                .toList();
    }

    public AppointmentResource cancelAppointment(long id) {
        log.info("Canceling appointment {}", id);
        AppointmentEntity entity = getAppointmentEntity(id);
        return resourceMapper.map(cancelAppointment(entity));
    }

    private AppointmentEntity cancelAppointment(AppointmentEntity entity) {
        Appointment appointment = domainMapper.map(entity);
        appointment.canceled();
        entity.setStatus(appointment.getStatus().name());
        return entity;
    }

    public List<TimeSlotResource> getAvailableSlots(long medOfficeId, long doctorId, LocalDate date, Duration duration) {
        log.info("Getting available slots with duration of {} minutes with the doctor {} in the medical office {}, for the day {}", duration.toMinutes(), doctorId, medOfficeId, date);
        WorkingDay workingDay = getWorkingDay(medOfficeId, doctorId, date);
        List<TimeSlot> availableTimeSlots = workingDay.getAvailableSlots(duration);
        return availableTimeSlots.stream().map(resourceMapper::map).toList();
    }

    public List<TimeSlotResource> getAvailableSlots(long medOfficeId, long doctorId, LocalDate from, LocalDate to, Duration duration) {
        log.info("Getting available slots with duration of {} minutes for the medical office {}, for the days between {} and {}", duration.toMinutes(), medOfficeId, from, to);
        WorkingPeriod workingPeriod = getWorkingPeriod(medOfficeId, doctorId, from, to);
        return workingPeriod.getAvailableSlots(duration).stream()
                .map(resourceMapper::map)
                .toList();
    }

    private WorkingPeriod getWorkingPeriod(long medOfficeId, long doctorId, LocalDate from, LocalDate toInclusive) {
        log.info("Getting WorkingPeriod of doctor {} in medical office {} between {} and {} inclusive", doctorId, medOfficeId, from, toInclusive);
        List<WorkingDay> workingDays = getWorkingDaysBetween(medOfficeId, doctorId, from, toInclusive)
                .stream()
                .sorted(Comparator.comparing(WorkingDay::getDate))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        Collections::unmodifiableList
                ));
        return new WorkingPeriod(from, toInclusive.plusDays(1), workingDays);
    }

    private WorkingDay getWorkingDay(long medOfficeId, long doctorId, LocalDate date) {
        log.info("Getting WorkingDay of doctor {} in medical office {} for date {}", doctorId, medOfficeId, date);
        List<WorkingTimeInterval> workingIntervals = getWorkingIntervals(medOfficeId, doctorId, date);
        return new WorkingDay(date, workingIntervals);
    }

    private AppointmentEntity getAppointmentEntity(long id) {
        return appointmentRepository
                .get(id)
                .orElseThrow(() -> {
                    log.info("Appointment with id : {} not found !", id);
                    return new NotFoundException(String.format("Appointment with id : %s not found !", id));
                });
    }

    private List<WorkingTimeInterval> getWorkingIntervals(long medOfficeId, long doctorId, LocalDate date) {
        log.info("Getting Working Intervals of doctor {} in medical office {} for date {}", doctorId, medOfficeId, date);
        List<WorkingIntervalEntity> entities = workingIntervalRepository.get(medOfficeId, doctorId, date);
        return entities.stream().map(domainMapper::map).toList();
    }

    private List<WorkingDay> getWorkingDaysBetween(long medOfficeId, long doctorId, LocalDate from, LocalDate toInclusive) {
        log.info("Getting Working Days of doctor {} in medical office {} between {} and {} inclusive", doctorId, medOfficeId, from, toInclusive);
        List<WorkingIntervalEntity> entities = workingIntervalRepository.get(medOfficeId, doctorId, from, toInclusive);
        return entities.stream()
                .collect(Collectors.groupingBy(WorkingIntervalEntity::getDate))
                .entrySet().stream()
                .map(e -> new WorkingDay(
                        e.getKey(),
                        e.getValue().stream().map(domainMapper::map)
                                .sorted(Comparator.comparing(WorkingTimeInterval::getStart))
                                .collect(Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        Collections::unmodifiableList
                                ))
                ))
                .toList();
    }
}
