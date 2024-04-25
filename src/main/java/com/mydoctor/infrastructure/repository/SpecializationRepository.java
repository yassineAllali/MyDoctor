package com.mydoctor.infrastructure.repository;

import com.mydoctor.infrastructure.entity.SpecializationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface SpecializationRepository extends JpaRepository<SpecializationEntity, Long> {

    Set<SpecializationEntity> findByIdIn(Collection<Long> ids);
}