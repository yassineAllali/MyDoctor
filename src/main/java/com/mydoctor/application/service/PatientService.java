package com.mydoctor.application.service;

import com.mydoctor.application.adapter.PatientRepositoryAdapter;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.mapper.EntityMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.PatientResource;
import com.mydoctor.application.resource.SpecializationResource;
import com.mydoctor.infrastructure.entity.PatientEntity;
import com.mydoctor.presentation.request.create.CreatePatientRequest;
import com.mydoctor.presentation.request.create.CreateSpecializationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PatientService implements ApplicationService<PatientResource, Long> {

    private final PatientRepositoryAdapter patientRepository;
    private final ResourceMapper resourceMapper;
    private final EntityMapper entityMapper;

    public PatientService(PatientRepositoryAdapter patientRepository) {
        this.patientRepository = patientRepository;
        resourceMapper = new ResourceMapper();
        entityMapper = new EntityMapper();
    }


    @Override
    public PatientResource get(Long id) throws NotFoundException {
        log.info("Getting patient with id {} !", id);
        PatientEntity entity = patientRepository
                .get(id)
                .orElseThrow(() -> new NotFoundException(String.format("Patient with id : %s not found !", id)));
        return resourceMapper.map(entity);
    }

    @Override
    public List<PatientResource> getAll() {
        log.info("Getting all patients !");
        List<PatientEntity> entities = patientRepository.getAll();
        return entities.stream()
                .map(resourceMapper::map)
                .toList();
    }

    @Override
    public PatientResource create(PatientResource resource) throws IllegalArgumentException {
        log.info("Creating new patient !");
        if(resource.id() != null) {
            throw new IllegalArgumentException("Id should not be set !");
        }
        return save(resource);
    }

    public PatientResource create(CreatePatientRequest createPatientRequest) {
        log.info("Creating new patient from request !");
        PatientResource patientResource = map(null, createPatientRequest);
        return create(patientResource);
    }

    @Override
    public PatientResource update(PatientResource resource) throws NotFoundException {
        log.info("Updating patient with id {} !", resource.id());
        checkExists(resource.id());
        return save(resource);
    }

    public PatientResource update(Long id, CreatePatientRequest createPatientRequest) {
        log.info("Updating patient from request !");
        PatientResource patientResource = map(id, createPatientRequest);
        return update(patientResource);
    }

    private PatientResource map(Long id, CreatePatientRequest createPatientRequest) {
        return PatientResource.builder()
                .id(id)
                .name(createPatientRequest.name())
                .build();
    }

    @Override
    public PatientResource save(PatientResource resource) {
        log.info("Saving patient !");
        PatientEntity entity = patientRepository.save(entityMapper.map(resource));
        return resourceMapper.map(entity);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.info("Deleting patient with id {} !", id);
        checkExists(id);
        patientRepository.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        log.info("Checking if patient with id {} exist !", id);
        return patientRepository.existById(id);
    }

    @Override
    public void checkExists(Long id) throws NotFoundException {
        if(!patientRepository.existById(id)) {
            log.info("Patient with id : {} not found !", id);
            throw new NotFoundException(String.format("Patient with id : %s not found !", id));
        }
    }
}

