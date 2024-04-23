package com.mydoctor.application.resource;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
public record WorkingIntervalResource(Long id, LocalDate date, LocalTime start, LocalTime end,
                                      List<AppointmentResource> appointments, MedicalOfficeResource medicalOffice,
                                      DoctorResource doctor) {
}
