package com.mydoctor.infrastructure.repository;

import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalOfficeRepository extends JpaRepository<MedicalOfficeEntity, Long>, JpaSpecificationExecutor<MedicalOfficeEntity> {
}
