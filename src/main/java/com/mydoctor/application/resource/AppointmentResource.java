package com.mydoctor.application.resource;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResource(Long id, PatientResource patient,
          MedicalOfficeResource medicalOffice, LocalDate date,
          LocalTime start, LocalTime end, String status) {
}
