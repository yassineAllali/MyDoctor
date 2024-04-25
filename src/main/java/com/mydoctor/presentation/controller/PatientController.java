package com.mydoctor.presentation.controller;

import com.mydoctor.application.service.PatientService;
import com.mydoctor.presentation.mapper.ResponseMapper;
import com.mydoctor.presentation.request.create.CreatePatientRequest;
import com.mydoctor.presentation.response.PatientResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final ResponseMapper responseMapper;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
        this.responseMapper = new ResponseMapper();
    }

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@RequestBody CreatePatientRequest createPatientRequest) {
        PatientResponse createdPatient = responseMapper.map(patientService.create(createPatientRequest));
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        List<PatientResponse> patients = patientService.getAll().stream()
                .map(responseMapper::map)
                .toList();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable("id") Long id) {
        PatientResponse patient = responseMapper.map(patientService.get(id));
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable("id") Long id, @RequestBody CreatePatientRequest patient) {
        PatientResponse updatedPatient = responseMapper.map(patientService.update(id, patient));
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) {
        patientService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
