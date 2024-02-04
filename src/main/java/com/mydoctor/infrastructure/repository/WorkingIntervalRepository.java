package com.mydoctor.infrastructure.repository;

import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkingIntervalRepository extends JpaRepository<WorkingIntervalEntity, Long> {

    List<WorkingIntervalEntity> findByMedicalOffice_IdAndDate(Long id, LocalDate date);

    List<WorkingIntervalEntity> findByMedicalOffice_IdAndDateAndStartBeforeAndEndAfter(Long id, LocalDate date, LocalTime start, LocalTime end);

    List<WorkingIntervalEntity> findByMedicalOffice_IdAndDateBetween(Long id, LocalDate dateStart, LocalDate dateEnd);
}