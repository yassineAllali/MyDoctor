package com.mydoctor.infrastructure.adapter;

import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.application.resource.WorkingIntervalResource;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import com.mydoctor.infrastructure.mapper.ApplicationMapper;
import com.mydoctor.infrastructure.repository.WorkingIntervalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class JpaWorkingIntervalRepositoryAdapter implements WorkingIntervalRepositoryAdapter {

    private final WorkingIntervalRepository repository;
    private final ApplicationMapper mapper;

    public JpaWorkingIntervalRepositoryAdapter(WorkingIntervalRepository repository) {
        this.repository = repository;
        mapper = new ApplicationMapper();
    }

    @Override
    public List<WorkingIntervalResource> get(Long medicalOfficeId, LocalDate date, LocalTime startBefore, LocalTime endAfter) {
        return repository.findByMedicalOffice_IdAndDateAndStartBeforeAndEndAfter(medicalOfficeId, date, startBefore, endAfter)
                .stream().map(mapper::map).toList();
    }

    @Override
    public List<WorkingIntervalResource> get(Long medicalOfficeId, LocalDate date) {
        List<WorkingIntervalEntity> workingIntervalEntities = repository.findByMedicalOffice_IdAndDate(medicalOfficeId,date);
        return workingIntervalEntities.stream().map(mapper::map).toList();
    }
}
