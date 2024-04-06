package com.mydoctor.infrastructure.repository;

import com.mydoctor.infrastructure.entity.SpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecializationRepository extends JpaRepository<SpecializationEntity, Long> {
}