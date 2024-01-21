package com.mydoctor.application.adapter;

import com.mydoctor.application.resource.WorkingIntervalResource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface WorkingIntervalRepositoryAdapter {
    List<WorkingIntervalResource> get(Long medicalOfficeId, LocalDate date, LocalTime startBefore, LocalTime endAfter);
    List<WorkingIntervalResource> get(Long medicalOfficeId, LocalDate date);
}
