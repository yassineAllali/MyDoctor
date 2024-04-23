package com.mydoctor.application.service;

import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.mapper.EntityMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.WorkingIntervalResource;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WorkingIntervalService implements ApplicationService<WorkingIntervalResource, Long> {

    private final WorkingIntervalRepositoryAdapter workingIntervalRepository;
    private final ResourceMapper resourceMapper;
    private final EntityMapper entityMapper;

    public WorkingIntervalService(WorkingIntervalRepositoryAdapter workingIntervalRepository) {
        this.workingIntervalRepository = workingIntervalRepository;
        resourceMapper = new ResourceMapper();
        entityMapper = new EntityMapper();
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
        return save(resource);
    }

    @Override
    public WorkingIntervalResource update(WorkingIntervalResource resource) throws NotFoundException {
        log.info("Updating working interval with id {} !", resource.id());
        checkExists(resource.id());
        return save(resource);
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

