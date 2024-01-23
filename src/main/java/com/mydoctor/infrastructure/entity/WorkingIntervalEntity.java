package com.mydoctor.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkingIntervalEntity {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medical_office_id")
    private MedicalOfficeEntity medicalOffice;

    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    @OneToMany(mappedBy = "workingInterval", fetch = FetchType.LAZY)
    private List<AppointmentEntity> appointments;
}
