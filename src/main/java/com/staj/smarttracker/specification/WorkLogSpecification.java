package com.staj.smarttracker.specification;

import com.staj.smarttracker.dto.WorkLogSearchCriteria;
import com.staj.smarttracker.entity.WorkLog;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class WorkLogSpecification {

    public static Specification<WorkLog> getWorkLogsByCriteria(WorkLogSearchCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();


            if (criteria.getDescription() != null && !criteria.getDescription().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("description")),
                        "%" + criteria.getDescription().toLowerCase() + "%"
                ));
            }


            if (criteria.getUserId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), criteria.getUserId()));
            }


            if (criteria.getFeatureId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("feature").get("id"), criteria.getFeatureId()));
            }


            if (criteria.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("logDate"), criteria.getStartDate()));
            }


            if (criteria.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("logDate"), criteria.getEndDate()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
