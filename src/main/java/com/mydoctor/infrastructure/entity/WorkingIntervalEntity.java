package com.mydoctor.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkingIntervalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctor;

    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "medical_office_id", nullable = false)
    private MedicalOfficeEntity medicalOffice;

    @OneToMany(mappedBy = "workingInterval", fetch = FetchType.LAZY)
    private List<AppointmentEntity> appointments;

}
