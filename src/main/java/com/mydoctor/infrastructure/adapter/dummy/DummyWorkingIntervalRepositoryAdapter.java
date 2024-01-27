package com.mydoctor.infrastructure.adapter.dummy;

import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import com.mydoctor.infrastructure.repository.WorkingIntervalRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public class DummyWorkingIntervalRepositoryAdapter implements WorkingIntervalRepositoryAdapter {

    private final WorkingIntervalRepository repository;

    public DummyWorkingIntervalRepositoryAdapter(WorkingIntervalRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<WorkingIntervalEntity> get(Long medicalOfficeId, LocalDate date, LocalTime startBefore, LocalTime endAfter) {
        return repository.findByMedicalOffice_IdAndDateAndStartBeforeAndEndAfter(medicalOfficeId, date, startBefore, endAfter);
    }

    @Override
    public List<WorkingIntervalEntity> get(Long medicalOfficeId, LocalDate date) {
        return repository.findByMedicalOffice_IdAndDate(medicalOfficeId,date);
    }
}
