package com.mydoctor.presentation.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponse(long id, LocalDate date, LocalTime start, LocalTime end, long medicalOfficeId, long doctorId, long patientId, String status) {
}
