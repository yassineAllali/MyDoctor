package com.mydoctor.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MedicalOfficeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private CityEntity city;

    @ManyToMany
    @JoinTable(name = "medOffice_specialization",
            joinColumns = @JoinColumn(name = "medicalOffice_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id"))
    private Set<SpecializationEntity> specializations = new LinkedHashSet<>();

}
