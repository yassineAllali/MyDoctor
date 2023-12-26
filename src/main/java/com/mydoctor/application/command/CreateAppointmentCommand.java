package com.mydoctor.application.command;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateAppointmentCommand(LocalDate date, LocalTime start, LocalTime end) {
}
