package com.mydoctor.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
public class AppointmentEntity {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_office_id")
    private MedicalOfficeEntity medicalOffice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "working_interval_id")
    private WorkingIntervalEntity workingInterval;

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

}
