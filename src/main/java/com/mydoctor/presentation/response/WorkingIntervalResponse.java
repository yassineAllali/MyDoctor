package com.mydoctor.presentation.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
public record WorkingIntervalResponse(Long id, LocalDate date, LocalTime start, LocalTime end,
                                      List<AppointmentResponse> appointments, MedicalOfficeResponse medicalOffice,
                                      DoctorResponse doctor) {
}
