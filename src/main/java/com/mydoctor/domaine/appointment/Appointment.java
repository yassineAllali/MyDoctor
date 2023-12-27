package com.mydoctor.domaine.appointment;

import com.mydoctor.domaine.exception.IllegalArgumentException;
import com.mydoctor.domaine.medical.MedicalOffice;
import com.mydoctor.domaine.medical.Patient;
import com.mydoctor.domaine.appointment.booking.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;

public final class Appointment {
    private final LocalDate date;
    private final TimeSlot timeSlot;

    public Appointment(LocalDate date, TimeSlot timeSlot) {
        validateInputs(date, timeSlot);
        this.date = date;
        this.timeSlot = timeSlot;
    }

    private void validateInputs(LocalDate date, TimeSlot timeSlot) {
        if(date == null)
            throw new IllegalArgumentException("Date is null !");
        if(timeSlot == null)
            throw new IllegalArgumentException("Time slot is null !");
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStart() {
        return timeSlot.getStart();
    }

    public LocalTime getEnd() {
        return timeSlot.getEnd();
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
}
