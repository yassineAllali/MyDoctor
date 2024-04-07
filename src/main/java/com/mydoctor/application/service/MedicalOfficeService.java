package com.mydoctor.application.service;

import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.command.MedicalOfficeSearchCriteriaCommand;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.mapper.EntityMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MedicalOfficeService implements ApplicationService<MedicalOfficeResource, Long> {

    private final MedicalOfficeRepositoryAdapter medicalOfficeRepository;
    private final ResourceMapper resourceMapper;
    private final EntityMapper entityMapper;

    public MedicalOfficeService(MedicalOfficeRepositoryAdapter medicalOfficeRepository) {
        this.medicalOfficeRepository = medicalOfficeRepository;
        resourceMapper = new ResourceMapper();
        entityMapper = new EntityMapper();
    }

    public List<MedicalOfficeResource> get(MedicalOfficeSearchCriteriaCommand searchCriteria) {
        log.info("Getting medical offices with search criteria");
        return medicalOfficeRepository.get(searchCriteria)
                .stream()
                .map(resourceMapper::map)
                .toList();
    }

    @Override
    public MedicalOfficeResource get(Long id) throws NotFoundException {
        log.info("Getting medical office with id {} !", id);
        MedicalOfficeEntity entity = medicalOfficeRepository
                .get(id)
                .orElseThrow(() -> new NotFoundException(String.format("Medical office with id : %s not found !", id)));
        return resourceMapper.map(entity);
    }

    @Override
    public List<MedicalOfficeResource> getAll() {
        log.info("Getting all medical offices !");
        List<MedicalOfficeEntity> entities = medicalOfficeRepository.getAll();
        return entities.stream()
                .map(resourceMapper::map)
                .toList();
    }

    @Override
    public MedicalOfficeResource create(MedicalOfficeResource resource) throws IllegalArgumentException {
        log.info("Creating new medical office !");
        if(resource.id() != null) {
            throw new IllegalArgumentException("Id should not be set !");
        }
        return save(resource);
    }

    @Override
    public MedicalOfficeResource update(MedicalOfficeResource resource) throws NotFoundException {
        log.info("Updating medical office with id {} !", resource.id());
        checkExists(resource.id());
        return save(resource);
    }

    @Override
    public MedicalOfficeResource save(MedicalOfficeResource resource) {
        log.info("Saving medical office !");
        MedicalOfficeEntity entity = medicalOfficeRepository.save(entityMapper.map(resource));
        return resourceMapper.map(entity);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.info("Deleting medical office with id {} !", id);
        checkExists(id);
        medicalOfficeRepository.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        log.info("Checking if medical office with id {} exist !", id);
        return medicalOfficeRepository.existById(id);
    }

    @Override
    public void checkExists(Long id) throws NotFoundException {
        if(!medicalOfficeRepository.existById(id)) {
            log.info("Medical office with id : {} not found !", id);
            throw new NotFoundException(String.format("Medical office with id : %s not found !", id));
        }
    }

}
