package com.mydoctor.domaine.appointment;

import com.mydoctor.domaine.medical.MedicalOffice;
import com.mydoctor.domaine.medical.Patient;
import com.mydoctor.domaine.appointment.booking.TimeSlot;

import java.time.LocalDate;

public final class Appointment {
    private final LocalDate date;
    private final TimeSlot timeSlot;
    private final Patient patient;

    public Appointment(LocalDate date, TimeSlot timeSlot, Patient patient) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.patient = patient;
    }

    public LocalDate getDate() {
        return date;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public Patient getPatient() {
        return patient;
    }
}
