package com.mydoctor.domaine.appointment.booking;

import java.time.Duration;

public interface Bookable {

    int getBookedSize();
    int getAvailableSlotsSize(Duration duration);
}
