package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface WorkingIntervalRepositoryAdapter {
    Optional<WorkingIntervalEntity> get(long id);
    List<WorkingIntervalEntity> getAll();
    WorkingIntervalEntity save(WorkingIntervalEntity entity);
    void delete(long id);
    boolean existById(long id);
    List<WorkingIntervalEntity> get(Long medicalOfficeId, Long doctorId, LocalDate date, LocalTime startBefore, LocalTime endAfter);
    List<WorkingIntervalEntity> get(Long medicalOfficeId, Long doctorId, LocalDate date);
    List<WorkingIntervalEntity> get(Long medicalOfficeId, Long doctorId, LocalDate from, LocalDate to);
}
