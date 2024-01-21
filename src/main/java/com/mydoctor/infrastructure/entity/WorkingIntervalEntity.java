package com.mydoctor.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
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
}
