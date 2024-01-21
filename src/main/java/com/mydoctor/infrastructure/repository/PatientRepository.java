package com.mydoctor.infrastructure.repository;

import com.mydoctor.infrastructure.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
}
