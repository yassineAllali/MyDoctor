package com.mydoctor.presentation.response;

import java.time.LocalTime;

public record TimeSlotResponse(LocalTime start, LocalTime end) {
}
