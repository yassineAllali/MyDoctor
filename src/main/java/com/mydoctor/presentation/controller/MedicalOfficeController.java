package com.mydoctor.presentation.controller;

import com.mydoctor.application.command.MedicalOfficeSearchCriteriaCommand;
import com.mydoctor.application.service.MedicalOfficeService;
import com.mydoctor.presentation.mapper.CommandMapper;
import com.mydoctor.presentation.mapper.ResponseMapper;
import com.mydoctor.presentation.request.MedicalOfficeSearchCriteriaRequest;
import com.mydoctor.presentation.response.MedicalOfficeResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/med-office")
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
}
