package com.mydoctor.application.adapter;

import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface WorkingIntervalRepositoryAdapter {
    List<WorkingIntervalEntity> get(Long medicalOfficeId, Long doctorId, LocalDate date, LocalTime startBefore, LocalTime endAfter);
    List<WorkingIntervalEntity> get(Long medicalOfficeId, Long doctorId, LocalDate date);
    List<WorkingIntervalEntity> get(Long medicalOfficeId, Long doctorId, LocalDate from, LocalDate to);
}
