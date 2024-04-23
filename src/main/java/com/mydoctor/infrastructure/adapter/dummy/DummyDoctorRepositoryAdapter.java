package com.mydoctor.infrastructure.adapter.dummy;

import com.mydoctor.application.adapter.DoctorRepositoryAdapter;
import com.mydoctor.infrastructure.entity.DoctorEntity;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
public class DummyDoctorRepositoryAdapter implements DoctorRepositoryAdapter {

    private final List<DoctorEntity> entities;

    public DummyDoctorRepositoryAdapter() {
        this.entities = generateDummyEntities();
    }

    private List<DoctorEntity> generateDummyEntities() {
        List<String> doctorNames = List.of(
                "Dr. Smith",
                "Dr. Johnson",
                "Dr. Williams",
                "Dr. Brown",
                "Dr. Jones",
                "Dr. Davis",
                "Dr. Miller",
                "Dr. Wilson",
                "Dr. Moore",
                "Dr. Taylor"
        );
        List<DoctorEntity> doctors = new ArrayList<>(10);
        for(int i = 0; i < doctorNames.size(); i++) {
            doctors.add(new DoctorEntity((long) i + 1, doctorNames.get(i), null));
        }
        return doctors;
    }

    @Override
    public Optional<DoctorEntity> get(Long id) {
        return entities.stream().filter(d -> d.getId().equals(id)).findFirst();
    }

    @Override
    public List<DoctorEntity> getAll() {
        return List.of();
    }

    @Override
    public DoctorEntity save(DoctorEntity entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existById(long id) {
        return false;
    }
}
