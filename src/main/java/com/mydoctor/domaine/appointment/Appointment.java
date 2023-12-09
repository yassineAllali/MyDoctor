package com.mydoctor.domaine.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public final class Appointment extends TimeSlot {

    public Appointment(LocalDate date, LocalTime start, LocalTime end) {
        super(date, start, end);
    }
}
