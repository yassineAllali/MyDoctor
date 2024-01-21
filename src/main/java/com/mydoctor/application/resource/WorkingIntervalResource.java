package com.mydoctor.application.resource;

import java.time.LocalDate;
import java.time.LocalTime;

public record WorkingIntervalResource(LocalDate date, LocalTime start, LocalTime end) {
}
