package com.mydoctor.infrastructure.adapter;

import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import com.mydoctor.infrastructure.repository.WorkingIntervalRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

//@Repository
public class JpaWorkingIntervalRepositoryAdapter implements WorkingIntervalRepositoryAdapter {

    private final WorkingIntervalRepository repository;

    public JpaWorkingIntervalRepositoryAdapter(WorkingIntervalRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<WorkingIntervalEntity> get(Long medicalOfficeId, Long doctorId, LocalDate date, LocalTime startBefore, LocalTime endAfter) {
        return repository.findByMedicalOffice_IdAndDoctor_IdAndDateAndStartBeforeAndEndAfter(medicalOfficeId, doctorId, date, startBefore, endAfter);
    }

    @Override
    public List<WorkingIntervalEntity> get(Long medicalOfficeId, Long doctorId, LocalDate date) {
        return repository.findByMedicalOffice_IdAndDoctor_IdAndDate(medicalOfficeId, doctorId, date);
    }

    @Override
    public List<WorkingIntervalEntity> get(Long medicalOfficeId, Long doctorId, LocalDate from, LocalDate to) {
        return repository.findByMedicalOffice_IdAndDoctor_IdAndDateBetween(medicalOfficeId, doctorId, from, to);
    }
}
