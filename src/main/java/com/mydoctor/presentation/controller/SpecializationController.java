package com.mydoctor.presentation.controller;

import com.mydoctor.application.service.SpecializationService;
import com.mydoctor.presentation.mapper.ResponseMapper;
import com.mydoctor.presentation.request.create.CreateSpecializationRequest;
import com.mydoctor.presentation.response.SpecializationResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specializations")
public class SpecializationController {

    private final SpecializationService specializationService;
    private final ResponseMapper responseMapper;

    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
        this.responseMapper = new ResponseMapper();
    }

    @PostMapping
    public ResponseEntity<SpecializationResponse> createSpecialization(@RequestBody CreateSpecializationRequest createSpecializationRequest) {
        SpecializationResponse createdSpecialization = responseMapper.map(specializationService.create(createSpecializationRequest));
        return new ResponseEntity<>(createdSpecialization, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SpecializationResponse>> getAllSpecializations() {
        List<SpecializationResponse> specializations = specializationService.getAll().stream()
                .map(responseMapper::map)
                .toList();
        return new ResponseEntity<>(specializations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecializationResponse> getSpecializationById(@PathVariable("id") Long id) {
        SpecializationResponse specialization = responseMapper.map(specializationService.get(id));
        return new ResponseEntity<>(specialization, HttpStatus.OK);
    }

    // Update a specialization
    @PutMapping("/{id}")
    public ResponseEntity<SpecializationResponse> updateSpecialization(@PathVariable("id") Long id, @RequestBody CreateSpecializationRequest specialization) {
        SpecializationResponse updatedSpecialization = responseMapper.map(specializationService.update(id, specialization));
        return new ResponseEntity<>(updatedSpecialization, HttpStatus.OK);
    }

    // Delete a specialization
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialization(@PathVariable("id") Long id) {
        specializationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

