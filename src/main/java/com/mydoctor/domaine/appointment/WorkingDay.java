package com.mydoctor.domaine.appointment;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WorkingDay {

    private final LocalDate date;

    public WorkingDay(LocalDate date) {
        this.date = date;
    }

    public DayOfWeek getDayOfWeek() {
        return DayOfWeek.from(date);
    }
}
