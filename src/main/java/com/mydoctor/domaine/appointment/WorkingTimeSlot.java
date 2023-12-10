package com.mydoctor.domaine.appointment;

import com.mydoctor.domaine.appointment.exception.TimeSlotNotBookedException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public final class WorkingTimeSlot extends TimeSlot {

    private final List<TimeSlot> booked;

    public WorkingTimeSlot(LocalTime start, LocalTime end, List<TimeSlot> booked) {
        super(start, end);
        this.booked = new ArrayList<>(booked);
    }

    public WorkingTimeSlot(LocalTime start, LocalTime end) {
        this(start, end, new ArrayList<>());
    }

    public List<TimeSlot> getBooked() {
        return new ArrayList<>(booked);
    }

    public void book(TimeSlot timeSlot) {
        if(timeSlot.isOutside(this)) {
            throw new TimeSlotNotBookedException("Outside working slot !");
        }
        booked.add(timeSlot);
    }

    public int getBookedSize() {
        return booked.size();
    }
}
