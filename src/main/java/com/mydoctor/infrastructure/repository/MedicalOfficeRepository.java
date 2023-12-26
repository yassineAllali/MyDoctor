package com.mydoctor.infrastructure.repository;

import com.mydoctor.infrastructure.repository.entity.MedicalOfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalOfficeRepository extends JpaRepository<MedicalOfficeEntity, Long> {
}
