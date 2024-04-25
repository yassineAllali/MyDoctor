package com.mydoctor.application.service;

import com.mydoctor.application.adapter.CityRepositoryAdapter;
import com.mydoctor.application.adapter.DoctorRepositoryAdapter;
import com.mydoctor.application.exception.IllegalArgumentException;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.mapper.EntityMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.CityResource;
import com.mydoctor.infrastructure.entity.CityEntity;
import com.mydoctor.presentation.request.create.CreateCityRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CityService implements ApplicationService<CityResource, Long> {

    private final CityRepositoryAdapter cityRepository;
    private final ResourceMapper resourceMapper;
    private final EntityMapper entityMapper;

    public CityService(CityRepositoryAdapter cityRepository) {
        this.cityRepository = cityRepository;
        this.resourceMapper = new ResourceMapper();
        this.entityMapper = new EntityMapper();
    }

    @Override
    public CityResource get(Long id) throws NotFoundException {
        log.info("Getting city with id {} !", id);
        CityEntity entity = cityRepository
                .get(id)
                .orElseThrow(() -> new NotFoundException(String.format("City with id : %s not found !", id)));
        return resourceMapper.map(entity);
    }

    @Override
    public List<CityResource> getAll() {
        log.info("Getting all cities !");
        List<CityEntity> entities = cityRepository.getAll();
        return entities.stream()
                .map(resourceMapper::map)
                .toList();
    }

    @Override
    public CityResource create(CityResource resource) throws IllegalArgumentException {
        log.info("Creating new city !");
        if(resource.id() != null) {
            throw new IllegalArgumentException("Id should not be set !");
        }
        return save(resource);
    }

    public CityResource create(CreateCityRequest createCityRequest) {
        log.info("Creating new city from request !");
        CityResource cityResource = map(null, createCityRequest);
        return create(cityResource);
    }

    @Override
    public CityResource update(CityResource resource) throws NotFoundException {
        log.info("Updating city with id {} !", resource.id());
        checkExists(resource.id());
        return save(resource);
    }

    public CityResource update(Long id, CreateCityRequest createCityRequest) {
        log.info("Updating city from request !");
        CityResource cityResource = map(id, createCityRequest);
        return update(cityResource);
    }

    private CityResource map(Long id, CreateCityRequest createCityRequest) {
        return CityResource.builder()
                .id(id)
                .name(createCityRequest.name())
                .build();
    }

    @Override
    public CityResource save(CityResource resource) {
        log.info("Saving city !");
        CityEntity entity = cityRepository.save(entityMapper.map(resource));
        return resourceMapper.map(entity);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        log.info("Deleting city with id {} !", id);
        checkExists(id);
        cityRepository.delete(id);
    }

    @Override
    public boolean exists(Long id) {
        log.info("Checking if city with id {} exist !", id);
        return cityRepository.existById(id);
    }

    @Override
    public void checkExists(Long id) throws NotFoundException {
        if(!cityRepository.existById(id)) {
            log.info("City with id : {} not found !", id);
            throw new NotFoundException(String.format("City with id : %s not found !", id));
        }
    }
}
