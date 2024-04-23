package com.mydoctor.presentation.controller;

import com.mydoctor.application.service.DoctorService;
import com.mydoctor.presentation.mapper.ResponseMapper;
import com.mydoctor.presentation.request.create.CreateDoctorRequest;
import com.mydoctor.presentation.response.DoctorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final ResponseMapper responseMapper;


    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
        this.responseMapper = new ResponseMapper();
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor(@RequestBody CreateDoctorRequest createDoctorRequest) {
        DoctorResponse createdDoctor = responseMapper.map(doctorService.create(createDoctorRequest));
        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        List<DoctorResponse> doctors = doctorService.getAll().stream()
                .map(responseMapper::map)
                .toList();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable("id") Long id) {
        DoctorResponse doctor = responseMapper.map(doctorService.get(id));
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    // Update a doctor
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> updateDoctor(@PathVariable("id") Long id, @RequestBody CreateDoctorRequest doctor) {
        DoctorResponse updatedDoctor = responseMapper.map(doctorService.update(id, doctor));
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

    // Delete a doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable("id") Long id) {
        doctorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
