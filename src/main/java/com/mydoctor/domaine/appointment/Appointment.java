package com.mydoctor.domaine.appointment;

import com.mydoctor.domaine.exception.IllegalArgumentException;
import com.mydoctor.domaine.exception.IllegalStateException;
import com.mydoctor.domaine.medical.MedicalOffice;
import com.mydoctor.domaine.medical.Patient;
import com.mydoctor.domaine.appointment.booking.TimeSlot;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public final class Appointment {
    private final LocalDate date;
    private final TimeSlot timeSlot;
    private Status status;

    public Appointment(LocalDate date, TimeSlot timeSlot, Status status) {
        validateInputs(date, timeSlot);
        this.date = date;
        this.timeSlot = timeSlot;
        this.status = status;
    }

    public Appointment(LocalDate date, TimeSlot timeSlot) {
        this(date, timeSlot, Status.CREATED);
    }

    private void validateInputs(LocalDate date, TimeSlot timeSlot) {
        if(date == null)
            throw new IllegalArgumentException("Date is null !");
        if(timeSlot == null)
            throw new IllegalArgumentException("Time slot is null !");
    }

    public LocalTime getStart() {
        return timeSlot.getStart();
    }

    public LocalTime getEnd() {
        return timeSlot.getEnd();
    }

    public void booked() {
        if(status != Status.CREATED) {
            throw new IllegalStateException(getIllegalStateMessage(Status.BOOKED));
        }
        status = Status.BOOKED;
    }

    public void canceled() {
        if(status != Status.BOOKED) {
            throw new IllegalStateException(getIllegalStateMessage(Status.CANCELED));
        }
        status = Status.CANCELED;
    }

    private String getIllegalStateMessage(Status to) {
        return String.format("Can't change status to %s, actual status %s !", to, status);
    }

    public enum Status {
        CREATED,
        BOOKED,
        CANCELED
    }
}
