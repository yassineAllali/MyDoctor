package com.mydoctor.application.service;

import com.mydoctor.application.adapter.SpecializationRepositoryAdapter;
import com.mydoctor.application.exception.IllegalArgumentException;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.mapper.EntityMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.SpecializationResource;
import com.mydoctor.infrastructure.entity.SpecializationEntity;
import com.mydoctor.presentation.request.create.CreateSpecializationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SpecializationService implements ApplicationService<SpecializationResource, Long> {

    private final SpecializationRepositoryAdapter specializationRepository;
    private final ResourceMapper resourceMapper;
    private final EntityMapper entityMapper;

    public SpecializationService(SpecializationRepositoryAdapter specializationRepository) {
        this.specializationRepository = specializationRepository;
        resourceMapper = new ResourceMapper();
        entityMapper = new EntityMapper();
    }


    @Override
    public SpecializationResource get(Long id) throws NotFoundException {
        log.info("Getting specialization with id {} !", id);
        SpecializationEntity entity = specializationRepository
                .get(id)
                .orElseThrow(() -> new NotFoundException(String.format("Specialization with id : %s not found !", id)));
        return resourceMapper.map(entity);
    }

    @Override
    public List<SpecializationResource> getAll() {
        log.info("Getting all specializations !");
        List<SpecializationEntity> entities = specializationRepository.getAll();
        return entities.stream()
                .map(resourceMapper::map)
                .toList();
    }

    @Override
    public SpecializationResource create(SpecializationResource resource) throws IllegalArgumentException {
        log.info("Creating new specialization !");
        if(resource.id() != null) {
            throw new IllegalArgumentException("Id should not be set !");
        }
        return save(resource);
    }

    public SpecializationResource create(CreateSpecializationRequest createSpecializationRequest) {
        log.info("Creating new specialization from request !");
        SpecializationResource specializationResource = map(null, createSpecializationRequest);
        return create(specializationResource);
    }

    @Override
    public SpecializationResource update(SpecializationResource resource) throws NotFoundException {
        log.info("Updating specialization with id {} !", resource.id());
        checkExists(resource.id());
        return save(resource);
    }

    public SpecializationResource update(Long id, CreateSpecializationRequest createSpecializationRequest) {
        log.info("Updating specialization from request !");
        SpecializationResource specializationResource = map(id, createSpecializationRequest);
        return update(specializationResource);
    }

    private SpecializationResource map(Long id, CreateSpecializationRequest createSpecializationRequest) {
        return SpecializationResource.builder()
                .id(id)
                .name(createSpecializationRequest.name())
                .build();
    }

    @Override
    public SpecializationResource save(SpecializationResource resource) {
        log.info("Saving specialization !");
        SpecializationEntity entity = specializationRepository.save(entityMapper.map(resource));
        return resourceMapper.map(entity);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.info("Deleting specialization with id {} !", id);
        checkExists(id);
        specializationRepository.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        log.info("Checking if specialization with id {} exist !", id);
        return specializationRepository.existById(id);
    }

    @Override
    public void checkExists(Long id) throws NotFoundException {
        if(!specializationRepository.existById(id)) {
            log.info("Specialization with id : {} not found !", id);
            throw new NotFoundException(String.format("Specialization with id : %s not found !", id));
        }
    }
}
