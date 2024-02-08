package com.mydoctor.infrastructure.repository.criteria;

import com.mydoctor.infrastructure.entity.MedicalOfficeEntity;
import com.mydoctor.infrastructure.entity.SpecializationEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class MedicalOfficeSpecification implements Specification<MedicalOfficeEntity> {
    private final MedicalOfficeSearchCriteria criteria;

    public MedicalOfficeSpecification(MedicalOfficeSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<MedicalOfficeEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (criteria.getName() != null && !criteria.getName().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + criteria.getName() + "%"));
        }

        if (criteria.getCityId() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("city").get("id"), criteria.getCityId()));
        }

        if (criteria.getSpecializationId() != null) {
            Join<MedicalOfficeEntity, SpecializationEntity> specializationJoin = root.join("specializations");
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(specializationJoin.get("id"), criteria.getSpecializationId()));
        }

        return predicate;
    }
}

