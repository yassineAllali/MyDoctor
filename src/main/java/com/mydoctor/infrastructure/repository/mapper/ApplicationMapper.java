package com.mydoctor.infrastructure.repository.mapper;

import com.mydoctor.application.resource.AppointmentResource;
import com.mydoctor.application.resource.MedicalOfficeResource;
import com.mydoctor.application.resource.PatientResource;
import com.mydoctor.infrastructure.repository.entity.AppointmentEntity;
import com.mydoctor.infrastructure.repository.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.repository.entity.PatientEntity;

public class ApplicationMapper {

    public AppointmentEntity map(AppointmentResource appointmentResource) {
        AppointmentEntity entity = new AppointmentEntity();

        entity.setId(appointmentResource.id());
        entity.setPatient(map(appointmentResource.patient()));
        entity.setMedicalOffice(map(appointmentResource.medicalOffice()));
        entity.setDate(appointmentResource.date());
        entity.setStart(appointmentResource.start());
        entity.setEnd(appointmentResource.end());

        return entity;
    }

    public PatientEntity map(PatientResource patientResource) {
        PatientEntity entity = new PatientEntity();
        entity.setId(patientResource.id());

        return entity;
    }

    public MedicalOfficeEntity map(MedicalOfficeResource medicalOfficeResource) {
        MedicalOfficeEntity entity = new MedicalOfficeEntity();
        entity.setId(medicalOfficeResource.id());

        return entity;
    }

    public AppointmentResource map(AppointmentEntity entity) {
        PatientResource patientResource = map(entity.getPatient());
        MedicalOfficeResource medicalOfficeResource = map(entity.getMedicalOffice());
        return new AppointmentResource(entity.getId(),
                patientResource, medicalOfficeResource, entity.getDate(), entity.getStart(), entity.getEnd());
    }

    public PatientResource map(PatientEntity entity) {
        return new PatientResource(entity.getId(), entity.getName());
    }

    public MedicalOfficeResource map(MedicalOfficeEntity entity) {
        return new MedicalOfficeResource(entity.getId(), entity.getName());
    }

}
