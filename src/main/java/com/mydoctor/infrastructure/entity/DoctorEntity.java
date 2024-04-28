package com.mydoctor.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.DETACH, optional = false)
    @JoinColumn(name = "specialization_id", nullable = false)
    private SpecializationEntity specializationEntity;

    @ManyToMany(mappedBy = "doctors", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    private Set<MedicalOfficeEntity> medicalOffices;

    public Set<MedicalOfficeEntity> getMedicalOffices() {
        return medicalOffices;
    }

    public void setMedicalOffices(Set<MedicalOfficeEntity> medicalOffices) {
        this.medicalOffices = medicalOffices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpecializationEntity getSpecializationEntity() {
        return specializationEntity;
    }

    public void setSpecializationEntity(SpecializationEntity specializationEntity) {
        this.specializationEntity = specializationEntity;
    }
}
