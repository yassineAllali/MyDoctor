package com.mydoctor.application.adapter;

import com.mydoctor.application.resource.AppointmentResource;

public interface AppointmentRepositoryAdapter {

    AppointmentResource save(AppointmentResource appointmentResource);
}
