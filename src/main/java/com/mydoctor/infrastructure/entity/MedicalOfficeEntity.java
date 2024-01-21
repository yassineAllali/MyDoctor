package com.mydoctor.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class MedicalOfficeEntity {

    @Id
    private Long id;

    private String name;
}
