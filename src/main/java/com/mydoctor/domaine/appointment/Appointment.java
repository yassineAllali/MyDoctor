package com.mydoctor.domaine.appointment;

import com.mydoctor.domaine.MedicalOffice;
import com.mydoctor.domaine.Patient;
import com.mydoctor.domaine.appointment.booking.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;

public final class Appointment {
    private final LocalDate date;
    private final TimeSlot timeSlot;
    private final Patient patient;
    private final MedicalOffice medicalOffice;

    public Appointment(LocalDate date, TimeSlot timeSlot, Patient patient, MedicalOffice medicalOffice) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.patient = patient;
        this.medicalOffice = medicalOffice;
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

    public MedicalOffice getMedicalOffice() {
        return medicalOffice;
    }
}
