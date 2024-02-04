package com.mydoctor.infrastructure.adapter.dummy;

import com.mydoctor.application.adapter.MedicalOfficeRepositoryAdapter;
import com.mydoctor.application.adapter.WorkingIntervalRepositoryAdapter;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.entity.WorkingIntervalEntity;
import com.mydoctor.infrastructure.repository.WorkingIntervalRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DummyWorkingIntervalRepositoryAdapter implements WorkingIntervalRepositoryAdapter {

    private final List<WorkingIntervalEntity> entities;
    private final MedicalOfficeRepositoryAdapter medicalOfficeRepositoryAdapter;
    private List<WorkingIntervalEntity> generateWorkingIntervalsForWeek() {
        List<WorkingIntervalEntity> workingIntervals = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2024, 1, 27);

        for (int i = 0; i < 7; i++) { // for each day in the week
            LocalDate currentDate = startDate.plusDays(i);
            // Duplicate the working intervals for the day
            for (int j = 0; j < 10; j++) {
                MedicalOfficeEntity medicalOffice = medicalOfficeRepositoryAdapter.get((long)j + 1).get();
                workingIntervals.add(new WorkingIntervalEntity(IdGenerator.generate(), medicalOffice, currentDate, LocalTime.of(8, 0), LocalTime.of(12, 0), new ArrayList<>()));
                workingIntervals.add(new WorkingIntervalEntity(IdGenerator.generate(), medicalOffice, currentDate, LocalTime.of(14, 0), LocalTime.of(18, 0), new ArrayList<>()));
            }
        }
        return workingIntervals;
    }

    public DummyWorkingIntervalRepositoryAdapter(MedicalOfficeRepositoryAdapter medicalOfficeRepositoryAdapter) {
        this.medicalOfficeRepositoryAdapter = medicalOfficeRepositoryAdapter;
        entities = generateWorkingIntervalsForWeek();
    }

    @Override
    public List<WorkingIntervalEntity> get(Long medicalOfficeId, LocalDate date, LocalTime startBefore, LocalTime endAfter) {
        List<WorkingIntervalEntity> workingIntervalEntities = get(medicalOfficeId, date);
        return workingIntervalEntities.stream()
                .filter(w -> w.getStart().isBefore(startBefore) && w.getEnd().isAfter(endAfter))
                .toList();
    }

    @Override
    public List<WorkingIntervalEntity> get(Long medicalOfficeId, LocalDate date) {
        return entities.stream().filter(w -> w.getDate().equals(date)
                && w.getMedicalOffice().getId().equals(medicalOfficeId))
                .toList();
    }

    @Override
    public List<WorkingIntervalEntity> get(Long medicalOfficeId, LocalDate from, LocalDate to) {
        return entities.stream().filter(e -> medicalOfficeId.equals(e.getMedicalOffice().getId()) && isBetween(e.getDate(), from, to)).toList();
    }

    private boolean isBetween(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
        return !dateToCheck.isBefore(startDate) && !dateToCheck.isAfter(endDate);
    }
}
