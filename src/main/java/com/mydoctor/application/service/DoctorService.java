package com.mydoctor.application.service;

import com.mydoctor.application.adapter.DoctorRepositoryAdapter;
import com.mydoctor.application.exception.IllegalArgumentException;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.mapper.EntityMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.DoctorResource;
import com.mydoctor.application.resource.SpecializationResource;
import com.mydoctor.infrastructure.entity.DoctorEntity;
import com.mydoctor.presentation.request.create.CreateDoctorRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DoctorService implements ApplicationService<DoctorResource, Long> {

    private final DoctorRepositoryAdapter doctorRepository;
    private final SpecializationService specializationService;
    private final ResourceMapper resourceMapper;
    private final EntityMapper entityMapper;

    public DoctorService(DoctorRepositoryAdapter doctorRepository, SpecializationService specializationService) {
        this.doctorRepository = doctorRepository;
        this.specializationService = specializationService;
        resourceMapper = new ResourceMapper();
        entityMapper = new EntityMapper();
    }


    @Override
    public DoctorResource get(Long id) throws NotFoundException {
        log.info("Getting doctor with id {} !", id);
        DoctorEntity entity = doctorRepository
                .get(id)
                .orElseThrow(() -> new NotFoundException(String.format("Doctor with id : %s not found !", id)));
        return resourceMapper.map(entity);
    }

    @Override
    public List<DoctorResource> getAll() {
        log.info("Getting all doctors !");
        List<DoctorEntity> entities = doctorRepository.getAll();
        return entities.stream()
                .map(resourceMapper::map)
                .toList();
    }

    @Override
    public DoctorResource create(DoctorResource resource) throws IllegalArgumentException {
        log.info("Creating new doctor !");
        if(resource.id() != null) {
            throw new IllegalArgumentException("Id should not be set !");
        }
        return save(resource);
    }

    public DoctorResource create(CreateDoctorRequest createDoctorRequest) {
        log.info("Creating new doctor from request !");
        DoctorResource doctorResource = map(null, createDoctorRequest);
        return create(doctorResource);
    }

    @Override
    public DoctorResource update(DoctorResource resource) throws NotFoundException {
        log.info("Updating doctor with id {} !", resource.id());
        checkExists(resource.id());
        return save(resource);
    }

    public DoctorResource update(Long id, CreateDoctorRequest createDoctorRequest) {
        log.info("Updating doctor from request !");
        DoctorResource doctorResource = map(id, createDoctorRequest);
        return update(doctorResource);
    }

    private DoctorResource map(Long id, CreateDoctorRequest createDoctorRequest) {
        SpecializationResource specializationResource = specializationService.get(createDoctorRequest.specializationId());
        return DoctorResource.builder()
                .id(id)
                .name(createDoctorRequest.name())
                .specialization(specializationResource)
                .build();
    }

    @Override
    public DoctorResource save(DoctorResource resource) {
        log.info("Saving doctor !");
        DoctorEntity entity = doctorRepository.save(entityMapper.map(resource));
        return resourceMapper.map(entity);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.info("Deleting doctor with id {} !", id);
        checkExists(id);
        doctorRepository.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        log.info("Checking if doctor with id {} exist !", id);
        return doctorRepository.existById(id);
    }

    @Override
    public void checkExists(Long id) throws NotFoundException {
        if(!doctorRepository.existById(id)) {
            log.info("Doctor with id : {} not found !", id);
            throw new NotFoundException(String.format("Doctor with id : %s not found !", id));
        }
    }
}
