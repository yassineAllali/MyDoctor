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

    List<WorkingIntervalEntity> findByMedicalOffice_IdAndDoctor_IdAndDate(Long medicalOfficeId, Long doctorId, LocalDate date);

    List<WorkingIntervalEntity> findByMedicalOffice_IdAndDoctor_IdAndDateBetween(Long medicalOfficeId, Long doctorId, LocalDate dateStart, LocalDate dateEnd);

    List<WorkingIntervalEntity> findByMedicalOffice_IdAndDoctor_IdAndDateAndStartBeforeAndEndAfter(Long id, Long id1, LocalDate date, LocalTime start, LocalTime end);
}