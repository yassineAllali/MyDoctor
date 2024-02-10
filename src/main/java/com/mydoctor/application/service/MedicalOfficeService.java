package com.mydoctor.application.service;

import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.command.MedicalOfficeSearchCriteriaCommand;
import com.mydoctor.application.mapper.ResourceMapper;
import com.mydoctor.application.resource.MedicalOfficeResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MedicalOfficeService {

    private final MedicalOfficeRepositoryAdapter medicalOfficeRepository;
    private final ResourceMapper resourceMapper;

    public MedicalOfficeService(MedicalOfficeRepositoryAdapter medicalOfficeRepository) {
        this.medicalOfficeRepository = medicalOfficeRepository;
        this.resourceMapper = new ResourceMapper();
    }

    public List<MedicalOfficeResource> get(MedicalOfficeSearchCriteriaCommand searchCriteria) {
        log.info("Getting medical offices with search criteria");
        return medicalOfficeRepository.get(searchCriteria)
                .stream()
                .map(resourceMapper::map)
                .toList();
    }

}
