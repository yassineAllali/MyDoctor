package com.mydoctor.application.resource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record WorkingIntervalResource(LocalDate date, LocalTime start, LocalTime end, List<AppointmentResource> appointments) {
}
