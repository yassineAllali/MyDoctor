package com.mydoctor.domaine.appointment;

import com.mydoctor.domaine.appointment.booking.BookablePeriod;
import com.mydoctor.domaine.appointment.booking.TimeSlot;
import com.mydoctor.domaine.appointment.booking.BookingException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public final class Scheduler {
    private final BookablePeriod workingPeriod;

    public Scheduler(BookablePeriod workingPeriod) {
        this.workingPeriod = workingPeriod;
    }

    public void schedule(Appointment appointment) throws BookingException {
        workingPeriod.book(appointment.getDate(), appointment.getTimeSlot());
    }

    public int getAppointmentsSize() {
        return workingPeriod.getBookedSize();
    }

    public List<TimeSlot> getAvailableSlots(Duration duration) {
        return workingPeriod.getAvailableSlots(duration);
    }

    public List<TimeSlot> getAvailableSlots(LocalDate date, Duration duration) {
        return workingPeriod.getAvailableSlots(date, duration);
    }

    public int getAvailableSlotsSize(Duration duration) {
        return workingPeriod.getAvailableSlotsSize(duration);
    }

}
