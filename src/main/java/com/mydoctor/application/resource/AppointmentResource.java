package com.mydoctor.application.resource;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record AppointmentResource(Long id, PatientResource patient,
          MedicalOfficeResource medicalOffice, DoctorResource doctor,
          WorkingIntervalResource workingInterval, LocalDate date,
          LocalTime start, LocalTime end, String status) {
}
