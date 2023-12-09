package com.mydoctor.domaine.appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public final class WorkingTimeSlot extends TimeSlot {

    private final List<Appointment> appointments;

    public WorkingTimeSlot(LocalDate date, LocalTime start, LocalTime end, List<Appointment> appointments) {
        super(date, start, end);
        this.appointments = new ArrayList<>(appointments);
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }
}
