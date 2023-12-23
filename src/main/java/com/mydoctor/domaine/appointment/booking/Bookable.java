package com.mydoctor.domaine.appointment.booking;

import java.time.Duration;
import java.util.List;

public interface Bookable {

    int getBookedSize();
    int getAvailableSlotsSize(Duration duration);
    List<TimeSlot> getAvailableSlots(Duration duration);
}
