package com.mydoctor.infrastructure.repository.specification;

import com.mydoctor.application.command.MedicalOfficeSearchCriteriaCommand;
import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class MedicalOfficeSpecificationBuilder {
    private final List<MedicalOfficeSearchCriteriaCommand> params;

    public MedicalOfficeSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public MedicalOfficeSpecificationBuilder with(MedicalOfficeSearchCriteriaCommand criteria) {
        params.add(criteria);
        return this;
    }

    public Specification<MedicalOfficeEntity> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<MedicalOfficeEntity> result = new MedicalOfficeSpecification(params.get(0));
        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new MedicalOfficeSpecification(params.get(i)));
        }
        return result;
    }
}

