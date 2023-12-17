package com.mydoctor.domaine.appointment;

import com.mydoctor.domaine.appointment.booking.TimeSlot;

import java.time.LocalTime;

public final class Appointment extends TimeSlot {

    public Appointment(LocalTime start, LocalTime end) {
        super(start, end);
    }
}
