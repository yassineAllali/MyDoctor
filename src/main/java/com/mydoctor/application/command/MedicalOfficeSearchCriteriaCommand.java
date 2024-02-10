package com.mydoctor.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicalOfficeSearchCriteriaCommand {
    private String name;
    private Long cityId;
    private Long specializationId;
}

