package com.mydoctor.presentation.controller;

import com.mydoctor.application.service.WorkingIntervalService;
import com.mydoctor.presentation.mapper.ResponseMapper;
import com.mydoctor.presentation.request.create.CreateWorkingIntervalRequest;
import com.mydoctor.presentation.response.WorkingIntervalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/working-intervals")
public class WorkingIntervalController {

    private final WorkingIntervalService workingIntervalService;
    private final ResponseMapper responseMapper;

    public WorkingIntervalController(WorkingIntervalService workingIntervalService) {
        this.workingIntervalService = workingIntervalService;
        this.responseMapper = new ResponseMapper();
    }

    @PostMapping
    public ResponseEntity<WorkingIntervalResponse> createWorkingInterval(@RequestBody CreateWorkingIntervalRequest createWorkingIntervalRequest) {
        WorkingIntervalResponse createdWorkingInterval = responseMapper.map(workingIntervalService.create(createWorkingIntervalRequest));
        return new ResponseEntity<>(createdWorkingInterval, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkingIntervalResponse>> getAllWorkingIntervals() {
        List<WorkingIntervalResponse> workingIntervals = workingIntervalService.getAll().stream()
                .map(responseMapper::map)
                .toList();
        return new ResponseEntity<>(workingIntervals, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkingIntervalResponse> getWorkingIntervalById(@PathVariable("id") Long id) {
        WorkingIntervalResponse workingInterval = responseMapper.map(workingIntervalService.get(id));
        return new ResponseEntity<>(workingInterval, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkingIntervalResponse> updateWorkingInterval(@PathVariable("id") Long id, @RequestBody CreateWorkingIntervalRequest workingInterval) {
        WorkingIntervalResponse updatedWorkingInterval = responseMapper.map(workingIntervalService.update(id, workingInterval));
        return new ResponseEntity<>(updatedWorkingInterval, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkingInterval(@PathVariable("id") Long id) {
        workingIntervalService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

