package com.mydoctor.application.command;

import lombok.Data;

@Data
public class MedicalOfficeSearchCriteriaCommand {
    private String name;
    private Long cityId;
    private Long specializationId;
}

