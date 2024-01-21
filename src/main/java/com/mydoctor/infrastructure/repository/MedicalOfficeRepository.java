package com.mydoctor.infrastructure.repository;

import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalOfficeRepository extends JpaRepository<MedicalOfficeEntity, Long> {
}
