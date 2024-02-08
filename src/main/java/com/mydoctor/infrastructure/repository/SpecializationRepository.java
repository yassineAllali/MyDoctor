package com.mydoctor.infrastructure.repository;

import com.mydoctor.infrastructure.entity.SpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecializationRepository extends JpaRepository<SpecializationEntity, Long> {
}