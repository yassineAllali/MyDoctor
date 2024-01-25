package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface WorkingIntervalRepositoryAdapter {
    List<WorkingIntervalEntity> get(Long medicalOfficeId, LocalDate date, LocalTime startBefore, LocalTime endAfter);
    List<WorkingIntervalEntity> get(Long medicalOfficeId, LocalDate date);
}
