package com.mydoctor.presentation.controller;

import com.mydoctor.application.command.MedicalOfficeSearchCriteriaCommand;
import com.mydoctor.application.service.MedicalOfficeService;
import com.mydoctor.presentation.mapper.CommandMapper;
import com.mydoctor.presentation.mapper.ResponseMapper;
import com.mydoctor.presentation.request.create.CreateMedicalOfficeRequest;
import com.mydoctor.presentation.request.MedicalOfficeSearchCriteriaRequest;
import com.mydoctor.presentation.response.MedicalOfficeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/med-offices")
public class MedicalOfficeController {

    private final MedicalOfficeService medicalOfficeService;
    private final CommandMapper commandMapper;
    private final ResponseMapper responseMapper;

    public MedicalOfficeController(MedicalOfficeService medicalOfficeService) {
        this.medicalOfficeService = medicalOfficeService;
        this.commandMapper = new CommandMapper();
        this.responseMapper = new ResponseMapper();
    }

    @PostMapping("/search")
    public List<MedicalOfficeResponse> search(@RequestBody MedicalOfficeSearchCriteriaRequest searchCriteriaRequest) {
        MedicalOfficeSearchCriteriaCommand searchCriteriaCommand = commandMapper.map(searchCriteriaRequest);
        return medicalOfficeService.get(searchCriteriaCommand).stream().map(responseMapper::map).toList();
    }

    @PostMapping
    public ResponseEntity<MedicalOfficeResponse> createMedicalOffice(@RequestBody CreateMedicalOfficeRequest createMedicalOfficeRequest) {
        MedicalOfficeResponse medicalOfficeResponse = responseMapper.map(medicalOfficeService.create(createMedicalOfficeRequest));
        return new ResponseEntity<>(medicalOfficeResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MedicalOfficeResponse>> getAllMedicalOffices() {
        List<MedicalOfficeResponse> medicalOffices = medicalOfficeService.getAll().stream()
                .map(responseMapper::map)
                .toList();
        return new ResponseEntity<>(medicalOffices, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalOfficeResponse> getMedicalOfficeById(@PathVariable("id") Long id) {
        MedicalOfficeResponse medicalOffice = responseMapper.map(medicalOfficeService.get(id));
        return new ResponseEntity<>(medicalOffice, HttpStatus.OK);
    }

    // Update a medicalOffice
    @PutMapping("/{id}")
    public ResponseEntity<MedicalOfficeResponse> updateMedicalOffice(@PathVariable("id") Long id, @RequestBody CreateMedicalOfficeRequest medicalOffice) {
        MedicalOfficeResponse updatedMedicalOffice = responseMapper.map(medicalOfficeService.update(id, medicalOffice));
        return new ResponseEntity<>(updatedMedicalOffice, HttpStatus.OK);
    }

    // Delete a medicalOffice
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalOffice(@PathVariable("id") Long id) {
        medicalOfficeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
