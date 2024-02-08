package com.mydoctor.infrastructure.repository;

import com.mydoctor.infrastructure.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
}