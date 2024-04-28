package com.mydoctor.application.service;

import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.command.MedicalOfficeSearchCriteriaCommand;
import com.mydoctor.application.exception.NotFoundException;
import com.mydoctor.application.mapper.EntityMapper;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.CityResource;
import com.mydoctor.application.resource.DoctorResource;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.application.resource.SpecializationResource;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.presentation.request.create.CreateMedicalOfficeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class MedicalOfficeService implements ApplicationService<MedicalOfficeResource, Long> {

    private final MedicalOfficeRepositoryAdapter medicalOfficeRepository;
    private final SpecializationService specializationService;
    private final CityService cityService;
    private final DoctorService doctorService;
    private final ResourceMapper resourceMapper;
    private final EntityMapper entityMapper;

    public MedicalOfficeService(MedicalOfficeRepositoryAdapter medicalOfficeRepository, SpecializationService specializationService, CityService cityService, DoctorService doctorService) {
        this.medicalOfficeRepository = medicalOfficeRepository;
        this.specializationService = specializationService;
        this.cityService = cityService;
        this.doctorService = doctorService;
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
        MedicalOfficeEntity entity = getEntity(id);
        return resourceMapper.map(entity);
    }

    private MedicalOfficeEntity getEntity(Long id) {
        return medicalOfficeRepository
                .get(id)
                .orElseThrow(() -> new NotFoundException(String.format("Medical office with id : %s not found !", id)));
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

    public MedicalOfficeResource create(CreateMedicalOfficeRequest createMedicalOfficeRequest) {
        log.info("Creating new medical office from request !");
        MedicalOfficeResource medicalOfficeResource = map(null, createMedicalOfficeRequest);
        return create(medicalOfficeResource);
    }

    @Override
    public MedicalOfficeResource update(MedicalOfficeResource resource) throws NotFoundException {
        log.info("Updating medical office with id {} !", resource.id());
        checkExists(resource.id());
        return save(resource);
    }

    public MedicalOfficeResource update(Long id, CreateMedicalOfficeRequest createMedicalOfficeRequest) {
        log.info("Updating medical office from request !");
        MedicalOfficeResource medicalOfficeResource = map(id, createMedicalOfficeRequest);
        return update(medicalOfficeResource);
    }

    private MedicalOfficeResource map(Long id, CreateMedicalOfficeRequest createMedicalOfficeRequest) {
        Set<SpecializationResource> specializationResources = specializationService.get(createMedicalOfficeRequest.specializationIds());
        CityResource cityResource = cityService.get(createMedicalOfficeRequest.cityId());
        return MedicalOfficeResource.builder()
                .id(id)
                .name(createMedicalOfficeRequest.name())
                .city(cityResource)
                .specializations(specializationResources)
                .build();
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

    public MedicalOfficeResource addDoctor(Long id, Long doctorId) {
        log.info("Adding doctor {} to medical office {} !", doctorId, id);

        checkExists(id);
        doctorService.checkExists(doctorId);

        MedicalOfficeResource medicalOffice = get(id);
        DoctorResource doctor = doctorService.get(doctorId);

        medicalOffice.doctors().add(doctor);

        return save(medicalOffice);
    }
}
