package com.mydoctor.infrastructure.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@Entity
public class MedicalOfficeEntity {

    @Id
    private Long id;

    private String name;
}
