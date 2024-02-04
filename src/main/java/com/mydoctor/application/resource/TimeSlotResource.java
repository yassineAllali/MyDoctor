package com.mydoctor.application.resource;

import java.time.LocalTime;

public record TimeSlotResource(LocalTime start, LocalTime end) {
}
