package com.mydoctor.application.service;

import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.application.exception.BusinessException;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.mapper.DomainMapper;
import com.mydoctor.application.mapper.EntityMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.DoctorResource;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.application.resource.PatientResource;
import com.mydoctor.application.resource.WorkingIntervalResource;
import com.mydoctor.domaine.appointment.booking.TimeSlot;
import com.mydoctor.domaine.appointment.booking.WorkingDay;
import com.mydoctor.domaine.appointment.booking.WorkingTimeInterval;
import com.mydoctor.domaine.exception.DomainException;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import com.mydoctor.presentation.request.create.CreatePatientRequest;
import com.mydoctor.presentation.request.create.CreateWorkingIntervalRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class WorkingIntervalService implements ApplicationService<WorkingIntervalResource, Long> {

    private final WorkingIntervalRepositoryAdapter workingIntervalRepository;
    private final DoctorService doctorService;
    private final MedicalOfficeService medicalOfficeService;
    private final ResourceMapper resourceMapper;
    private final EntityMapper entityMapper;
    private final DomainMapper domainMapper;

    public WorkingIntervalService(WorkingIntervalRepositoryAdapter workingIntervalRepository, DoctorService doctorService, MedicalOfficeService medicalOfficeService) {
        this.workingIntervalRepository = workingIntervalRepository;
        this.doctorService = doctorService;
        this.medicalOfficeService = medicalOfficeService;
        resourceMapper = new ResourceMapper();
        entityMapper = new EntityMapper();
        domainMapper = new DomainMapper();
    }


    @Override
    public WorkingIntervalResource get(Long id) throws NotFoundException {
        log.info("Getting working interval with id {} !", id);
        WorkingIntervalEntity entity = workingIntervalRepository
                .get(id)
                .orElseThrow(() -> new NotFoundException(String.format("Working interval with id : %s not found !", id)));
        return resourceMapper.map(entity);
    }

    @Override
    public List<WorkingIntervalResource> getAll() {
        log.info("Getting all working intervals !");
        List<WorkingIntervalEntity> entities = workingIntervalRepository.getAll();
        return entities.stream()
                .map(resourceMapper::map)
                .toList();
    }

    @Override
    public WorkingIntervalResource create(WorkingIntervalResource resource) throws IllegalArgumentException {
        log.info("Creating new working interval !");
        if(resource.id() != null) {
            throw new IllegalArgumentException("Id should not be set !");
        }
        checkWorkingDayForNew(resource);
        return save(resource);
    }

    private WorkingDay getWorkingDayWithNew(WorkingIntervalResource workingIntervalResource) {
        List<WorkingIntervalEntity> existingIntervalEntities = workingIntervalRepository.get(
                workingIntervalResource.medicalOffice().id(),
                workingIntervalResource.doctor().id(),
                workingIntervalResource.date());
        List<WorkingTimeInterval> existingTimeIntervals = existingIntervalEntities.stream()
                .map(domainMapper::map)
                .toList();
        WorkingTimeInterval newWorkingTimeInterval = domainMapper.map(workingIntervalResource);
        List<WorkingTimeInterval> workingDayTimeIntervals = Stream.concat(existingTimeIntervals.stream(), Stream.of(newWorkingTimeInterval))
                .sorted(Comparator.comparing(WorkingTimeInterval::getStart))
                .toList();
        return new WorkingDay(workingIntervalResource.date(), workingDayTimeIntervals);
    }

    private void checkWorkingDayForNew(WorkingIntervalResource workingIntervalResource) {
        try {
            getWorkingDayWithNew(workingIntervalResource);
        } catch (DomainException ex) {
            String message = "Can't create working interval : " + ex.getMessage();
            log.error(message);
            throw new BusinessException(message);
        }
    }

    public WorkingIntervalResource create(CreateWorkingIntervalRequest createWorkingIntervalRequest) {
        log.info("Creating new working interval from request !");
        WorkingIntervalResource workingIntervalResource = map(null, createWorkingIntervalRequest);
        return create(workingIntervalResource);
    }

    @Override
    public WorkingIntervalResource update(WorkingIntervalResource resource) throws NotFoundException {
        log.info("Updating working interval with id {} !", resource.id());
        checkExists(resource.id());
        return save(resource);
    }

    public WorkingIntervalResource update(Long id, CreateWorkingIntervalRequest createWorkingIntervalRequest) {
        log.info("Updating working interval from request !");
        WorkingIntervalResource workingIntervalResource = map(id, createWorkingIntervalRequest);
        return update(workingIntervalResource);
    }

    private WorkingIntervalResource map(Long id, CreateWorkingIntervalRequest createWorkingIntervalRequest) {
        DoctorResource doctorResource = doctorService.get(createWorkingIntervalRequest.doctorId());
        MedicalOfficeResource medicalOfficeResource = medicalOfficeService.get(createWorkingIntervalRequest.medicalOfficeId());
        return WorkingIntervalResource.builder()
                .id(id)
                .doctor(doctorResource)
                .medicalOffice(medicalOfficeResource)
                .date(createWorkingIntervalRequest.date())
                .start(createWorkingIntervalRequest.start())
                .end(createWorkingIntervalRequest.end())
                .build();
    }

    @Override
    public WorkingIntervalResource save(WorkingIntervalResource resource) {
        log.info("Saving working interval !");
        WorkingIntervalEntity entity = workingIntervalRepository.save(entityMapper.map(resource));
        return resourceMapper.map(entity);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.info("Deleting working interval with id {} !", id);
        checkExists(id);
        workingIntervalRepository.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        log.info("Checking if working interval with id {} exist !", id);
        return workingIntervalRepository.existById(id);
    }

    @Override
    public void checkExists(Long id) throws NotFoundException {
        if(!workingIntervalRepository.existById(id)) {
            log.info("Working interval with id : {} not found !", id);
            throw new NotFoundException(String.format("Working interval with id : %s not found !", id));
        }
    }
}

