package com.uos.dsd.cinema.application.service.director;

import org.springframework.data.jpa.domain.Specification;

import com.uos.dsd.cinema.domain.director.Director;

public class DirectorSpecification {

    public static Specification<Director> notDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<Director> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),
                "%" + name + "%");
    }
}