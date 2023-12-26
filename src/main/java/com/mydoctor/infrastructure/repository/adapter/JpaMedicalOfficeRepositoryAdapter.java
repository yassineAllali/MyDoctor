package com.mydoctor.infrastructure.repository.adapter;

import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.infrastructure.repository.MedicalOfficeRepository;
import com.mydoctor.infrastructure.repository.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.repository.mapper.ApplicationMapper;
import org.springframework.stereotype.Service;

@Service
public class JpaMedicalOfficeRepositoryAdapter implements MedicalOfficeRepositoryAdapter {

    private final MedicalOfficeRepository repository;
    private final ApplicationMapper mapper;

    public JpaMedicalOfficeRepositoryAdapter(MedicalOfficeRepository repository) {
        this.repository = repository;
        this.mapper = new ApplicationMapper();
    }

    @Override
    public MedicalOfficeResource save(MedicalOfficeResource medicalOfficeResource) {
        MedicalOfficeEntity entity = mapper.map(medicalOfficeResource);
        return mapper.map(repository.save(entity));
    }
}
