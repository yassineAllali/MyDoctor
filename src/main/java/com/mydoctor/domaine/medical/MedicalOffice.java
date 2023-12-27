package com.mydoctor.domaine.medical;

import com.mydoctor.domaine.appointment.Appointment;
import com.mydoctor.domaine.appointment.Scheduler;
import com.mydoctor.domaine.appointment.booking.TimeSlot;
import com.mydoctor.domaine.appointment.booking.BookingException;
import com.mydoctor.domaine.medical.contact.Contact;
import com.mydoctor.domaine.medical.location.Address;

import java.time.Duration;
import java.util.List;

public final class MedicalOffice {
    private final String name;
    private final String description;
    private final Address address;
    private final Contact contact;
    private final List<Doctor> doctors;

    public MedicalOffice(String name, String description, Address address, Contact contact, List<Doctor> doctors) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.contact = contact;
        this.doctors = doctors;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Address getAddress() {
        return address;
    }

    public Contact getContact() {
        return contact;
    }

    public List<Doctor> getDoctors() {
        return List.copyOf(doctors);
    }

    List<Speciality> getSpecialities() {
        return doctors.stream().map(d -> d.speciality()).toList();
    }
}
