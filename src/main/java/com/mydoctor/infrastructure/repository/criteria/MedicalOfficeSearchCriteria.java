package com.mydoctor.infrastructure.repository.criteria;

import lombok.Data;

@Data
public class MedicalOfficeSearchCriteria {
    private String name;
    private Long cityId;
    private Long specializationId;
}

