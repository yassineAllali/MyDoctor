package com.mydoctor.infrastructure.adapter.dummy;

import com.mydoctor.application.adapter.AppointmentRepositoryAdapter;
import com.mydoctor.infrastructure.entity.AppointmentEntity;
import com.mydoctor.infrastructure.repository.AppointmentRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

//@Repository
public class DummyAppointmentRepositoryAdapter implements AppointmentRepositoryAdapter {

    private final List<AppointmentEntity> appointmentEntities;

    private List<AppointmentEntity> generateDummyAppointments() {
        List<AppointmentEntity> appointments = new ArrayList<>();
        Random random = new Random();

        // Start generating appointments from today
        LocalDate startDate = LocalDate.of(2024, 1, 27);
        LocalDate endDate = startDate.plusDays(6); // One week from today

        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            // Generate 5 to 10 appointments for each day
            int numberOfAppointments = random.nextInt(6) + 5;

            LocalTime baseStartTime = LocalTime.of(8, 0);

            for (int i = 0; i < numberOfAppointments; i++) {
                // Add a random gap between 0 to 60 minutes
                int gapMinutes = random.nextInt(61);
                baseStartTime = baseStartTime.plusMinutes(gapMinutes);

                LocalTime endTime = baseStartTime.plusMinutes(20);

                // Create an appointment entity
                AppointmentEntity appointment = new AppointmentEntity();
                appointment.setId(IdGenerator.generate());
                appointment.setDate(date);
                appointment.setStart(baseStartTime);
                appointment.setEnd(endTime);

                // Add the appointment to the list
                appointments.add(appointment);

                // Move the base start time to the end time of the current appointment
                baseStartTime = endTime;
            }
        }

        return appointments;
    }

    public DummyAppointmentRepositoryAdapter() {
        this.appointmentEntities = generateDummyAppointments();
    }

    @Override
    public AppointmentEntity save(AppointmentEntity appointmentEntity) {
        appointmentEntity.setId(IdGenerator.generate());
        appointmentEntities.add(appointmentEntity);
        return appointmentEntity;
    }

    @Override
    public List<AppointmentEntity> getPatientAppointments(long patientId) {
        return appointmentEntities.stream()
                .filter(a -> a.getPatient() != null && a.getPatient().getId() != null && a.getPatient().getId().equals(patientId))
                .toList();
    }

    @Override
    public Optional<AppointmentEntity> get(long id) {
        return appointmentEntities.stream().filter(a -> a.getId() == id).findFirst();
    }

    @Override
    public List<AppointmentEntity> getAll() {
        return List.of();
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public boolean existById(long id) {
        return false;
    }

}
