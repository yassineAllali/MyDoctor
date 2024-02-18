package com.mydoctor.presentation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateAppointmentRequest(
        @Schema(example = "2024-01-01", format = "date", description = "Appointment date in YYYY-MM-DD format")LocalDate date,
        @Schema(example = "10:30:00", format = "time", type = "String", description = "Start time in HH:mm:ss format")LocalTime start,
        @Schema(example = "11:00:00", format = "time", type = "String", description = "End time in HH:mm:ss format")LocalTime end) {
}
