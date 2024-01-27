package com.mydoctor.presentation.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateAppointmentRequest(LocalDate date, LocalTime start, LocalTime end) {
}
